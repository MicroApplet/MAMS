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

package com.asialjim.microapplet.mams.user.config;

import com.asialjim.microapplet.commons.config.core.ConfType;
import com.asialjim.microapplet.commons.config.core.TypedConfiguration;
import lombok.Data;

import java.io.Serializable;
import java.time.Duration;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * 用户登录令牌配置信息
 *
 * @author <a href="mailto:asialjim@hotmail.com">Asial Jim</a>
 * @version 1.0
 * @since 2025/2/24, &nbsp;&nbsp; <em>version:1.0</em>
 */
@Data
@TypedConfiguration(
        type = ConfType.System,
        business = "sys",
        code = "jwt:commons",
        codeName = "通用JWT配置信息",
        desc = "JWT令牌配置信息"
)
public class JwtConfProperty implements Serializable {
    private String secret = "abc";
    private Long timeout = 5L;
    private TimeUnit timeUnit = TimeUnit.HOURS;

    public Duration jwtTimeout() {
        return Duration.ofSeconds(timeUnit().toSeconds(timeout()));
    }

    private long timeout() {
        return Optional.ofNullable(this.timeout).orElse(5L);
    }

    private TimeUnit timeUnit() {
        return Optional.ofNullable(this.timeUnit).orElse(TimeUnit.MINUTES);
    }
}