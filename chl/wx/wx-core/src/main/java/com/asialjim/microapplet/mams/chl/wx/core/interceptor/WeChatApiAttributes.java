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

package com.asialjim.microapplet.mams.chl.wx.core.interceptor;

import org.springframework.http.HttpRequest;

import java.util.Map;
import java.util.Objects;

/**
 * 微信接口请求属性
 *
 * @author Asial Jim
 * @version 1.0
 * @since 2026/6/24, &nbsp;&nbsp; <em>version:1.0</em>
 */
public final class WeChatApiAttributes {
    public static final String APPID = "wechat.appid";
    public static final String SKIP_ACCESS_TOKEN = "wechat.skipAccessToken";

    private WeChatApiAttributes() {
    }

    public static String appid(HttpRequest request) {
        Object value = attributes(request).get(APPID);
        return Objects.isNull(value) ? null : String.valueOf(value);
    }

    public static boolean skipAccessToken(HttpRequest request) {
        Object value = attributes(request).get(SKIP_ACCESS_TOKEN);
        if (value instanceof Boolean bool)
            return bool;
        return Objects.nonNull(value) && Boolean.parseBoolean(String.valueOf(value));
    }

    private static Map<String, Object> attributes(HttpRequest request) {
        return request.getAttributes();
    }
}
