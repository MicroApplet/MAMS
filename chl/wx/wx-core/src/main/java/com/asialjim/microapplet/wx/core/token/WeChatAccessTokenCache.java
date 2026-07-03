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

package com.asialjim.microapplet.wx.core.token;

import org.apache.commons.lang3.StringUtils;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 内存版微信 access_token 缓存
 *
 * @author Asial Jim
 * @version 1.0
 * @since 2026/6/24, &nbsp;&nbsp; <em>version:1.0</em>
 */
@Component
public class WeChatAccessTokenCache implements WeChatCache {
    private static final Map<String, Lock> locks = new ConcurrentHashMap<>();

    @Override
    @Cacheable(value = WeChatCache.accessTokenByAppid, key = "#appid")
    public String get(String appid) {
        return StringUtils.EMPTY;
    }

    @Override
    @CachePut(value = WeChatCache.accessTokenByAppid, key = "#appid")
    public String set(String appid, String accessToken) {
        return accessToken;
    }

    @Override
    @CacheEvict(value = WeChatCache.accessTokenByAppid, key = "#appid")
    public void remove(String appid) {
    }

    @Override
    public Lock lock(String appid) {
        return locks.computeIfAbsent(appid, _ -> new ReentrantLock());
    }
}
