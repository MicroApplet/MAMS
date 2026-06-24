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

package com.asialjim.microapplet.mams.chl.wx.core.client;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.Duration;

/**
 * 微信客户端配置
 *
 * @author Asial Jim
 * @version 1.0
 * @since 2026/6/24, &nbsp;&nbsp; <em>version:1.0</em>
 */
@Data
@ConfigurationProperties(prefix = "mams.chl.wx")
public class WeChatClientProperties {
    private String baseUrl = "https://api.weixin.qq.com";
    private Duration accessTokenTtl = Duration.ofMinutes(110);
    private Duration accessTokenLockTtl = Duration.ofSeconds(5);
}
