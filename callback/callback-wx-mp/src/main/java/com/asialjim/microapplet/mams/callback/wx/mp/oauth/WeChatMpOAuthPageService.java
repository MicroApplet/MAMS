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

package com.asialjim.microapplet.mams.callback.wx.mp.oauth;

import com.asialjim.microapplet.web.client.MamsHttpHeaders;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.time.Duration;
import java.util.List;

/**
 * 微信网页授权页面服务
 *
 * @author Asial Jim
 * @version 1.0
 * @since 2026/6/24, &nbsp;&nbsp; <em>version:1.0</em>
 */
@Service
@RequiredArgsConstructor
public class WeChatMpOAuthPageService {
    private final List<WeChatMpOAuthHandler> handlers;
    private final WeChatMpOAuthLoginService loginService;
    private final WxMpOAuthProperties properties;

    public ResponseEntity<Void> page(String appid, String handlerName, String code, String state) {
        WeChatMpOAuthHandler handler = handlers.stream()
                .filter(item -> item.handler().equalsIgnoreCase(handlerName))
                .findFirst()
                .orElseGet(handlers::getFirst);
        URI uri = handler.page(appid, state);
        String token = loginService.login(appid, code);
        ResponseCookie cookie = ResponseCookie.from(MamsHttpHeaders.USER_TOKEN_KEY, token)
                .httpOnly(true)
                .secure(true)
                .sameSite("Lax")
                .path("/")
                .maxAge(Duration.ofSeconds(properties.getCookieMaxAgeSeconds()))
                .build();
        return ResponseEntity.status(302)
                .header(HttpHeaders.LOCATION, uri.toString())
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .header(MamsHttpHeaders.USER_TOKEN_KEY, token)
                .header(MamsHttpHeaders.Authorization, token)
                .build();
    }
}
