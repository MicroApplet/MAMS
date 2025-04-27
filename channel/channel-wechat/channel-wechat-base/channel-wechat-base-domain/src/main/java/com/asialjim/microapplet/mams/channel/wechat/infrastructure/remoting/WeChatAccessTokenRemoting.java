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
package com.asialjim.microapplet.mams.channel.wechat.infrastructure.remoting;


import com.asialjim.microapplet.mams.channel.wechat.infrastructure.remoting.meta.accesstoken.*;
import com.asialjim.microapplet.remote.http.annotation.HttpMapping;
import com.asialjim.microapplet.remote.http.annotation.HttpMethod;
import com.asialjim.microapplet.remote.http.annotation.HttpQuery;
import com.asialjim.microapplet.remote.http.annotation.body.JsonBody;
import com.asialjim.microapplet.remote.net.annotation.Server;
import com.asialjim.microapplet.mams.channel.wechat.constant.WeChatCons;

/**
 * 微信公众平台API令牌相关网络客户端
 *
 * @author <a href="mailto:asialjim@hotmail.com">Asial Jim</a>
 * @version 1.0
 * @since 2025/4/25, &nbsp;&nbsp; <em>version:1.0</em>
 */
@SuppressWarnings("unused")
@Server(
        supplier = WeChatCons.Supplier.Tencent,
        namespace = WeChatCons.Namespace.WeChat,
        schema = WeChatCons.Api.DefaultSchema,
        host = WeChatCons.Api.DefaultWeChatHost,
        port = WeChatCons.Api.DefaultWeChatPort
)
public interface WeChatAccessTokenRemoting {

    /**
     * 获取接口令牌
     *
     * @param appid  {@link String appid}
     * @param secret {@link String secret}
     * @return {@link WeChatAccessTokenRes}
     * @since 2023/12/14
     */
    @HttpMapping(method = HttpMethod.GET, uri = "/cgi-bin/token", queries = @HttpQuery(name = "grant_type", value = "client_credential"))
    WeChatAccessTokenRes accessToken(@HttpQuery(name = "appid") String appid, @HttpQuery(name = "secret") String secret);

    /**
     * 获取稳定的接口调用令牌
     *
     * @return {@link WeChatAccessTokenRes}
     * @since 2023/12/16
     */
    @HttpMapping(method = HttpMethod.POST, uri = "/cgi-bin/stable_token", queries = @HttpQuery(name = "grant_type", value = "client_credential"))
    WeChatAccessTokenRes stableToken(@JsonBody StableTokenReq req);

    /**
     * 令牌（component_access_token）是第三方平台接口的调用凭据。令牌的获取是有限制的，每个令牌的有效期为 2 小时，请自行做好令牌的管理，在令牌快过期时（比如1小时50分），重新调用接口获取。
     * 如未特殊说明，令牌一般作为被调用接口的 GET 参数 component_access_token 的值使用
     *
     * @param req {@link ComponentAccessTokenReq req}
     * @return {@link ComponentAccessTokenRes }
     * @since 2023/12/26
     */
    @HttpMapping(method = HttpMethod.POST, uri = "/cgi-bin/component/api_component_token")
    ComponentAccessTokenRes componentAccessToken(@JsonBody ComponentAccessTokenReq req);

    /**
     * <pre>
     *功能描述
     * 该接口用于获取授权账号的authorizer_access_token。authorizer_access_token 有效期为 2 小时，authorizer_access_token 失效时，可以使用 authorizer_refresh_token 获取新的 authorizer_access_token。使用过程中如遇到问题，可在开放平台服务商专区发帖交流。
     * 注意事项
     * authorizer_access_token 有效期为 2 小时，开发者需要缓存 authorizer_access_token，避免 API 调用触发每日限额。
     * 缓存方法可以参考：<a href="https://developers.weixin.qq.com/doc/offiaccount/Basic_Information/Get_access_token.html">文档</a>
     * </pre>
     *
     * @param token {@link String token}
     * @param req   {@link AuthorizerAccessTokenReq req}
     * @return {@link AuthorizerAccessTokenRes }
     * @since 2023/12/26
     */
    @HttpMapping(method = HttpMethod.POST, uri = "/cgi-bin/component/api_authorizer_token")
    AuthorizerAccessTokenRes authorizerAccessToken(@HttpQuery(name = "component_access_token") String token, @JsonBody AuthorizerAccessTokenReq req);
}