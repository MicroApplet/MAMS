/*
 *    Copyright 2014-2025 <a href="mailto:asialjim@qq.com">Asial Jim</a>
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package com.asialjim.microapplet.mams.wx.common.infrastructure.repository;

import com.asialjim.microapplet.mams.wx.common.infrastructure.cache.MamsWxCommonCache;
import com.asialjim.microapplet.wechat.remoting.context.WeChatAccessTokenCache;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 微信API令牌缓存器
 *
 * @author <a href="mailto:asialjim@hotmail.com">Asial Jim</a>
 * @version 1.0
 * @since 2025/9/23, &nbsp;&nbsp; <em>version:1.0</em>
 */
@Component
@AllArgsConstructor
public class MamsWeChatAccessTokenCache implements WeChatAccessTokenCache {
    private final StringRedisTemplate stringRedisTemplate;

    /**
     * 集
     *
     * @param appid       appid
     * @param accessToken 访问令牌
     */
    @Override
    @CachePut(value = MamsWxCommonCache.Name.wechatApiAccessToken, key = "#appid")
    public String set(String appid, String accessToken) {
        return accessToken;
    }

    /**
     * 得到
     *
     * @param appid appid
     * @return {@link String}
     */
    @Override
    @Cacheable(value = MamsWxCommonCache.Name.wechatApiAccessToken, key = "#appid")
    public String get(String appid) {
        return "";
    }

    /**
     * 删除
     *
     * @param appid appid
     */
    @Override
    @CacheEvict(value = MamsWxCommonCache.Name.wechatApiAccessToken, key = "#appid")
    public void remove(String appid) {
    }

    /**
     * 缓存
     *
     * @param appid appid
     * @return {@link String}
     */
    @Override
    public String cached(String appid) {
        return "";
    }

    /**
     * 得到锁
     *
     * @param appid appid
     * @return {@link Lock}
     */
    @Override
    public Lock getLock(String appid) {
        return new UUidLock(this.stringRedisTemplate, appid, UUID.randomUUID().toString());
    }

    @AllArgsConstructor
    static class UUidLock extends ReentrantLock {
        private static final String KEY = "tmp:wechat:api:access-token:lock:";
        private final StringRedisTemplate stringRedisTemplate;
        private final String appid;
        private final String uuid;

        @Override
        public void lock() {
            boolean tag;
            do {
                tag = tryLock(5, TimeUnit.SECONDS);
            } while (!tag);
        }

        @Override
        public boolean tryLock(long time, TimeUnit unit) {
            String key = KEY + appid;
            Boolean ok = stringRedisTemplate.opsForValue().setIfAbsent(key, uuid, time, unit);
            return Boolean.TRUE.equals(ok);
        }

        @Override
        public void unlock() {
            String key = KEY + appid;
            // Lua 脚本保证原子性
            String lua = "if redis.call('get', KEYS[1]) == ARGV[1] then " +
                    "  return redis.call('del', KEYS[1]) " +
                    "else return 0 end";
            stringRedisTemplate.execute(new DefaultRedisScript<>(lua, Long.class),
                    Collections.singletonList(key), uuid);
        }
    }
}