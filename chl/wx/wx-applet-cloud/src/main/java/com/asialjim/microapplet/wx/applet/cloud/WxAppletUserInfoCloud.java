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

package com.asialjim.microapplet.wx.applet.cloud;

import com.asialjim.microapplet.web.client.annotation.HttpExchangeClient;
import com.asialjim.microapplet.wx.applet.WxAppletCons;
import com.asialjim.microapplet.wx.applet.api.WxAppletUserInfoApi;
import com.asialjim.microapplet.wx.applet.session.WxAppletUserSession;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

@HttpExchange(WxAppletUserInfoApi.path)
@HttpExchangeClient(WxAppletCons.app_name)
public interface WxAppletUserInfoCloud extends WxAppletUserInfoApi {


    @GetExchange("/login")
    WxAppletUserSession login(@RequestParam String appid, @RequestParam String code);

}
