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

package com.asialjim.microapplet.user.agreement.infrastructure.cache;

import com.asialjim.microapplet.cache.core.strategy.CacheStrategy;
import com.asialjim.microapplet.cache.core.strategy.CacheStrategyCollection;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 渠道用户缓存
 *
 * @author Asial Jim
 * @version 1.0
 * @since 2026/2/6, &nbsp;&nbsp; <em>version:1.0</em>
 */
public interface AgreementCache {
    String userAgreementRecord = "mb:user:agreement:record";


    @Getter
    @AllArgsConstructor
    enum Strategy implements CacheStrategy {
        userAgreementRecord(AgreementCache.userAgreementRecord,
                false,
                Duration.ZERO,
                Duration.ofMinutes(30),
                Duration.ZERO),
        ;


    private final String name;
    private final boolean localAvailable;
    private final Duration localTTL;
    private final Duration TTL;
    private final Duration nullTTL;
    }

    @Configuration
    class AgreementCacheStrategyCollection extends CacheStrategyCollection {
        @Override
        public Set<CacheStrategy> set() {
            return Arrays.stream(Strategy.values()).collect(Collectors.toSet());
        }
    }
}