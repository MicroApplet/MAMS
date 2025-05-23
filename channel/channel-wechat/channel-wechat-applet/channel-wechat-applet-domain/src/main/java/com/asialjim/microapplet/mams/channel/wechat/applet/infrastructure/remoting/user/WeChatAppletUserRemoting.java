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

package com.asialjim.microapplet.mams.channel.wechat.applet.infrastructure.remoting.user;

import com.asialjim.microapplet.mams.channel.wechat.applet.infrastructure.remoting.user.meta.WeChatAppletUserLoginRes;
import com.asialjim.microapplet.mams.channel.wechat.constant.WeChatCons;
import com.asialjim.microapplet.remote.http.annotation.HttpMapping;
import com.asialjim.microapplet.remote.http.annotation.HttpMethod;
import com.asialjim.microapplet.remote.http.annotation.HttpQuery;
import com.asialjim.microapplet.remote.net.annotation.Server;

/**
 * 微信小程序用户远程服务
 *
 * @author <a href="mailto:asialjim@hotmail.com">Asial Jim</a>
 * @version 1.0
 * @since 2025/5/23, &nbsp;&nbsp; <em>version:1.0</em>
 */
@Server(
        supplier = WeChatCons.Supplier.Tencent,
        namespace = WeChatCons.Namespace.WeChat,
        schema = WeChatCons.Api.DefaultSchema,
        host = WeChatCons.Api.DefaultWeChatHost,
        port = WeChatCons.Api.DefaultWeChatPort
)
public interface WeChatAppletUserRemoting {

    @HttpMapping(method = HttpMethod.GET,uri = "/sns/jscode2session", queries = @HttpQuery(name = "grant_type", value = "client_credential"))
    WeChatAppletUserLoginRes login(@HttpQuery(name = "appid") String appid, @HttpQuery(name = "secret") String secret, @HttpQuery(name = "js_code")String code);
}