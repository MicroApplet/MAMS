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

package io.github.microapplet.mams.user;

import io.github.microapplet.mams.user.jwt.JwtTokenCache;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 用户模型Bean扫描
 *
 * @author <a href="mailto:asialjim@hotmail.com">Asial Jim</a>
 * @version 1.0
 * @since 2025/3/4, &nbsp;&nbsp; <em>version:1.0</em>
 */
@Configuration
@ComponentScan
public class UserSessionBean {

    @Bean
    @ConditionalOnBean
    @ConditionalOnMissingBean
    public JwtTokenCache scheduledLocalJwtTokenCache(ScheduledExecutorService scheduledExecutorService) {
        return new JwtTokenCache() {
            private final Map<String, String> cache = new ConcurrentHashMap<>();

            @Override
            public void set(String authorization, String jwtToken, Duration timeout) {
                if (StringUtils.isAnyBlank(authorization, jwtToken))
                    return;
                cache.put(authorization, jwtToken);

                if (Objects.nonNull(timeout)) {
                    scheduledExecutorService.schedule(() -> cache.remove(authorization), timeout.getSeconds(), TimeUnit.SECONDS);
                }
            }

            @Override
            public String get(String authorization) {
                return cache.get(authorization);
            }

            @Override
            public void delete(String authorization) {
                cache.remove(authorization);
            }
        };
    }

    @Bean
    @ConditionalOnMissingBean
    public JwtTokenCache localJwtTokenCache() {
        return new JwtTokenCache() {
            private final Map<String, String> cache = new ConcurrentHashMap<>();

            @Override
            public void set(String authorization, String jwtToken, Duration timeout) {
                if (StringUtils.isAnyBlank(authorization, jwtToken))
                    return;
                cache.put(authorization, jwtToken);
            }

            @Override
            public String get(String authorization) {
                return cache.get(authorization);
            }

            @Override
            public void delete(String authorization) {
                cache.remove(authorization);
            }
        };
    }
}