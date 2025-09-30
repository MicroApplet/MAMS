/*
 *    Copyright 2014-2025 <a href="mailto:asialjim@qq.com">Asial Jim</a>
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package com.asialjim.microapplet.mams.user.security;

import com.asialjim.microapplet.mams.user.infrastructure.config.JwtConfigProperty;
import com.asialjim.microapplet.mams.user.infrastructure.repository.SessionRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtRequestFilter extends OncePerRequestFilter {
    private final MamsUserDetailsService userDetailsService;
    private final JwtConfigProperty jwtConfigProperty;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        final String authorization = authorization(request);
        String jwtToken;

        if (StringUtils.isNotBlank(authorization))
            jwtToken = authorization;
        else
            jwtToken = getTokenFromCookie(request);

        UserDetails userDetails = null;
        if (StringUtils.isNotBlank(jwtToken)) {
            try {
                this.jwtConfigProperty.verify(jwtToken);
                userDetails = this.userDetailsService.loadUserByUsername(jwtToken);
            } catch (Throwable ignored) {

            }
        }

        MamsAuthentication mamsAuthentication = new MamsAuthentication((MamsSessionUserDetails) userDetails);
        SecurityContextHolder.getContext().setAuthentication(mamsAuthentication);
        chain.doFilter(request, response);
    }

    private String authorization(HttpServletRequest req) {
        String authorization = req.getHeader("Authorization");
        if (StringUtils.isNotBlank(authorization))
            return authorization.replaceFirst("Bearer ", StringUtils.EMPTY);

        authorization = req.getHeader("authorization");
        if (StringUtils.isNotBlank(authorization))
            return authorization.replaceFirst("Bearer ", StringUtils.EMPTY);

        return StringUtils.EMPTY;
    }

    private String getTokenFromCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("x-user-token".equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return StringUtils.EMPTY;
    }

    public SessionRepository getSessionRepository() {
        return sessionRepository;
    }
}