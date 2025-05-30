/*
 *  Copyright 2014-2025 <a href="mailto:asialjim@qq.com">Asial Jim</a>
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *   limitations under the License.
 */

package com.asialjim.microapplet.mams.applet.infrastructure.cache;

import com.asialjim.microapplet.common.cache.CacheNameAndTTL;
import com.asialjim.microapplet.common.cache.CacheNameAndTTLConfig;
import lombok.Getter;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;

/**
 * 应用程序缓存名配置
 *
 * @author <a href="mailto:asialjim@hotmail.com">Asial Jim</a>
 * @version 1.0
 * @since 2025/4/15, &nbsp;&nbsp; <em>version:1.0</em>
 */
@Configuration
public class AppletCacheName extends CacheNameAndTTLConfig {
    public interface Name {
        String appletById = "tmp:applet:by-id";
        String chlAppletsByAppletId = "tmp:chl:apps:by-applet-id";
        String chlAppletByChlAppId = "tmp:chl:app:by-chl-app-id";
        String chlAppletById = "tmp:chl:app:by-id";
        String chlAppletByIndex = "tmp:chl:app:by-index";
        String chlAppletByAppidAndType = "tmp:chl:app:by-app-id-and-type";
    }

    @Getter
    enum Cache implements CacheNameAndTTL {
        placeholder("placeholder"),
        chlAppletByAppidAndType(Name.chlAppletByAppidAndType,Duration.ofDays(1L)),
        chlAppletByIndex(Name.chlAppletByIndex,Duration.ofDays(1L)),
        chlAppletById(Name.chlAppletById, Duration.ofDays(1L)),
        chlAppletsById(Name.chlAppletsByAppletId, Duration.ofDays(1L)),
        chlAppletByChlAppId(Name.chlAppletByChlAppId, Duration.ofDays(1L)),
        appletById(Name.appletById, Duration.ofDays(1L));

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