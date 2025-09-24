/*
 * Copyright 2014-2025 <a href="mailto:asialjim@qq.com">Asial Jim</a>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.asialjim.microapplet.mams.user.infrastructure.security;

import com.asialjim.microapplet.common.exception.UnLoginException;
import com.asialjim.microapplet.commons.security.web.SessionSecurityManager;
import com.asialjim.microapplet.mams.user.domain.agg.SessionUser;
import com.asialjim.microapplet.mams.user.infrastructure.config.JwtConfProperty;
import com.asialjim.microapplet.mams.user.infrastructure.repository.JwtTokenRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

import jakarta.annotation.Resource;

/**
 * 安全管理器
 *
 * @author <a href="mailto:asialjim@hotmail.com">Asial Jim</a>
 * @version 1.0
 * @since 2025/4/11, &nbsp;&nbsp; <em>version:1.0</em>
 */
@Component
public class SessionSecurityManagerImpl implements SessionSecurityManager {

    @Resource
    private JwtConfProperty jwtConfProperty;
    @Resource
    private JwtTokenRepository jwtTokenRepository;


    @Override
    public boolean skip(HttpServletRequest request) {
        return false;
       /*
        String url = request.getRequestURI();
        String context = contextPath();
        return this.knife4jSkipParseRes2ResultConfig.skipParseRes2ResultCheck(url, context);
        */
    }

    @Override
    public void doLoginCheck(HttpServletRequest request) throws UnLoginException {
        SessionUser.verify(request, jwtConfProperty, jwtTokenRepository);
    }

    /*private String contextPath() {
        return Optional.ofNullable(this.serverProperties)
                .map(ServerProperties::getServlet)
                .map(ServerProperties.Servlet::getContextPath)
                .orElse(StringUtils.EMPTY);
    }*/
}