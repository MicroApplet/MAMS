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

package com.asialjim.microapplet.mams.app.infrastructure.cache;

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
public class AppletCache extends CacheNameAndTTLConfig {
    public interface Name {
        String appPoById = "tmp:app:po:by-id";
        String appPoByOrgId = "tmp:app:po:by-id";
        String appVosByName = "tmp:app:vos:by-name";

        String chlAppPoById = "tmp:chl-app:po:by-id";
        String chlAppPoByOrgId = "tmp:chl-app:po:by-org-id";
        String chlAppPoByAppid = "tmp:chl-app:po:by-appid";
        String chlAppPoByAppidAndChl = "tmp:chl-app:po:by-appid-and-chl";

        String chlAppPoByChlAndChlAppid = "tmp:chl-app:po:by-chl-and-chl-appid";
        String chlAppPoByChlAndChlIndex = "tmp:chl-app:po:by-chl-and-chl-index";
        String chlAppPoByAppidChlAndChlAppid = "tmp:chl-app:po:by-appid-chl-and-chl-appid";
        String chlAppPoByAppidChlAndChlAppType = "tmp:chl-app:po:by-appid-chl-and-chl-appType";

        String chlAppPoByChl = "tmp:chl-app:po:by-chl";


        String orgPoById = "tmp:org:po:by-id";
    }

    @Getter
    enum Cache implements CacheNameAndTTL {
        appPoById(Name.appPoById, Duration.ofDays(7)),
        appPoByOrgId(Name.appPoByOrgId, Duration.ofHours(7)),
        appVosByName(Name.appVosByName,Duration.ofHours(2)),

        chlAppPoById(Name.chlAppPoById, Duration.ofDays(7)),
        chlAppPoByOrgId(Name.chlAppPoByOrgId, Duration.ofDays(7)),
        chlAppPoByAppid(Name.chlAppPoByAppid, Duration.ofDays(7)),
        chlAppPoByAppidAndChl(Name.chlAppPoByAppidAndChl, Duration.ofDays(7)),
        chlAppPoByChlAndChlAppid(Name.chlAppPoByChlAndChlAppid, Duration.ofDays(7)),
        chlAppPoByChlAndChlIndex(Name.chlAppPoByChlAndChlIndex, Duration.ofDays(7)),
        chlAppPoByAppidChlAndChlAppid(Name.chlAppPoByAppidChlAndChlAppid, Duration.ofDays(7)),
        chlAppPoByAppidChlAndChlAppType(Name.chlAppPoByAppidChlAndChlAppType,Duration.ofDays(7)),
        chlAppPoByChl(Name.chlAppPoByChl,Duration.ofDays(7)),

        orgPoById(Name.orgPoById, Duration.ofDays(7)),
        placeholder("placeholder");

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

    /**
     * 列表
     *
     * @return {@link List<CacheNameAndTTL>}
     */
    @Override
    protected List<CacheNameAndTTL> list() {
        return Arrays.asList(Cache.values());
    }
}