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

package com.asialjim.microapplet.mams.callback.wx.mp;

import com.asialjim.microapplet.spring.App;
import com.asialjim.microapplet.web.client.annotation.EnableHttpExchangeClients;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 微信公众号回调服务
 *
 * @author Asial Jim
 * @version 1.0
 * @since 2026/6/24, &nbsp;&nbsp; <em>version:1.0</em>
 */
@SpringBootApplication
@EnableHttpExchangeClients({"com.asialjim.microapplet.app", "com.asialjim.microapplet.user"})
public class WxMpCallback {
    public static void main(String[] args) {
        App.voidStart(WxMpCallback.class, args);
    }
}
