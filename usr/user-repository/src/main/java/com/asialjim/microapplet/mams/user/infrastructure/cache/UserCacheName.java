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

package com.asialjim.microapplet.mams.user.infrastructure.cache;

import com.asialjim.microapplet.common.cache.CacheNameAndTTL;
import com.asialjim.microapplet.common.cache.CacheNameAndTTLConfig;
import lombok.Getter;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;

/**
 * 用户缓存方案
 *
 * @author <a href="mailto:asialjim@hotmail.com">Asial Jim</a>
 * @version 1.0
 * @since 2025/4/11, &nbsp;&nbsp; <em>version:1.0</em>
 */
@Configuration
public class UserCacheName extends CacheNameAndTTLConfig {
    public interface Name {
        String userChl = "tmp:user:chl";
        String userMain = "tmp:user:main";
        String idCardByIdNo = "tmp:user:id-card";
        String idCardById = "tmp:user:id";
        String userIdCardCache = "tmp:id-card:session";
    }

    @Getter
    enum Cache implements CacheNameAndTTL {
        idCardByIdNo(Name.idCardByIdNo),
        idCardById(Name.idCardById),
        // 会话-证件信息缓存时间： 5分钟
        userIdCardCache(Name.userIdCardCache, Duration.ofMinutes(5L)),
        userMain(Name.userMain),
        userChl(Name.userChl);


        Cache(String name) {
            this(name, Duration.ofHours(1));
        }

        Cache(String name, Duration nonNullTTL) {
            this(name, nonNullTTL, Duration.ofMinutes(1));
        }

        Cache(String name, Duration nonNullTTL, Duration nullTTL) {
            this.name = name;
            this.nonNullTTL = nonNullTTL;
            this.nullTTL = nullTTL;
        }

        private final String name;
        private final Duration nonNullTTL;
        private final Duration nullTTL;
    }


    @Override
    protected List<CacheNameAndTTL> list() {
        return Arrays.asList(Cache.values());
    }
}