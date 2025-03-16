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

package com.asialjim.microapplet.mams.user.session;

import com.asialjim.microapplet.mams.user.jwt.JwtTokenCache;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.Duration;

/**
 * 用户会话令牌缓存器
 *
 * @author <a href="mailto:asialjim@hotmail.com">Asial Jim</a>
 * @version 1.0
 * @since 2025 03 16, &nbsp;&nbsp; <em>version:1.0</em>
 */
@Component
public class JwtTokenCacheImpl implements JwtTokenCache {
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public void set(String authorization, String jwtToken, Duration timeout) {
        this.stringRedisTemplate.opsForValue().set(authorizationKey(authorization), jwtToken, timeout);
    }

    @Override
    public String get(String authorization) {
        return this.stringRedisTemplate.opsForValue().get(authorizationKey(authorization));
    }

    @Override
    public void delete(String authorization) {
        this.stringRedisTemplate.delete(authorizationKey(authorization));
    }

    private String authorizationKey(String authorization) {
        return "tmp:session:" + authorization;
    }

}
