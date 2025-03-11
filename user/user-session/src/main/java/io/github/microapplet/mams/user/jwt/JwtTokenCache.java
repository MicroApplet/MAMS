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

package io.github.microapplet.mams.user.jwt;

import java.time.Duration;

/**
 * JWT令牌缓存工具
 *
 * @author <a href="mailto:asialjim@hotmail.com">Asial Jim</a>
 * @version 1.0
 * @since 2025/2/24, &nbsp;&nbsp; <em>version:1.0</em>
 */
public interface JwtTokenCache {

    /**
     * 缓存 JWT 令牌
     * @param authorization 用户令牌：用于负载到 HTTP HEADER Authorization 中
     * @param jwtToken JWT 令牌，存储用户信息，登录时间，登录过期时间
     * @param timeout 超时时间
     */
    void set(String authorization, String jwtToken, Duration timeout);

    /**
     * 获取 JWT 缓存
     */
    String get(String authorization);
}