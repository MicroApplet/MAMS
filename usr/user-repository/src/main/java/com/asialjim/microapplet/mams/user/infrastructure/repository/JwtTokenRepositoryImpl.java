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

package com.asialjim.microapplet.mams.user.infrastructure.repository;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import jakarta.annotation.Resource;
import java.time.Duration;

/**
 * JWT令牌存储
 *
 * @author <a href="mailto:asialjim@hotmail.com">Asial Jim</a>
 * @version 1.0
 * @since 2025/4/11, &nbsp;&nbsp; <em>version:1.0</em>
 */
@Component
public class JwtTokenRepositoryImpl implements JwtTokenRepository {
    private static final String tmp = "TMP:JWT:%s";
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public void set(String authorization, String jwtToken, Duration timeout) {
        String key = String.format(tmp, authorization);
        stringRedisTemplate.opsForValue().set(key, jwtToken, timeout);
    }

    @Override
    public String get(String authorization) {
        String key = String.format(tmp, authorization);
        return stringRedisTemplate.opsForValue().get(key);
    }

    @Override
    public void delete(String authorization) {
        String key = String.format(tmp, authorization);
        stringRedisTemplate.delete(key);
    }
}