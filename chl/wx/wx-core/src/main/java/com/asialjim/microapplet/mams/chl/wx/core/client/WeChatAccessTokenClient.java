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

import com.asialjim.microapplet.mams.chl.wx.core.context.WeChatAccessTokenRes;
import com.asialjim.microapplet.mams.chl.wx.core.interceptor.WeChatApiAttributes;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

/**
 * 微信 access_token 客户端
 *
 * @author Asial Jim
 * @version 1.0
 * @since 2026/6/24, &nbsp;&nbsp; <em>version:1.0</em>
 */
@HttpExchange
public interface WeChatAccessTokenClient {
    @GetExchange("/cgi-bin/token")
    WeChatAccessTokenRes token(@RequestAttribute(WeChatApiAttributes.SKIP_ACCESS_TOKEN) boolean skipAccessToken,
                               @RequestParam("grant_type") String grantType,
                               @RequestParam("appid") String appid,
                               @RequestParam("secret") String secret);
}
