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
package com.asialjim.microapplet.mams.channel.wechat.official.infrastructure.remoting.customer;

import com.asialjim.microapplet.mams.channel.wechat.constant.WeChatCons;
import com.asialjim.microapplet.mams.channel.wechat.infrastructure.remoting.accesstokken.WeChatAccessTokenParam;
import com.asialjim.microapplet.mams.channel.wechat.infrastructure.remoting.meta.BaseWeChatApiRes;
import com.asialjim.microapplet.mams.channel.wechat.official.infrastructure.remoting.customer.meta.*;
import com.asialjim.microapplet.remote.http.annotation.HttpMapping;
import com.asialjim.microapplet.remote.http.annotation.HttpMethod;
import com.asialjim.microapplet.remote.http.annotation.HttpQuery;
import com.asialjim.microapplet.remote.http.annotation.body.FormData;
import com.asialjim.microapplet.remote.http.annotation.body.JsonBody;
import com.asialjim.microapplet.remote.net.annotation.Server;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

/**
 * 客服消息
 *
 * @author <a href="mailto:asialjim@hotmail.com">Asial Jim</a>
 * @version 1.0
 * @since 2025/5/7, &nbsp;&nbsp; <em>version:1.0</em>
 */
@Server(
        supplier = WeChatCons.Supplier.Tencent,
        namespace = WeChatCons.Namespace.WeChat,
        schema = WeChatCons.Api.DefaultSchema,
        host = WeChatCons.Api.DefaultWeChatHost,
        port = WeChatCons.Api.DefaultWeChatPort
)
public interface WeChatPaCustomerMessageRemoting {

    /**
     * 添加客服账号
     *
     * @param weChatIndex {@link String weChatIndex}
     * @param body        {@link CustomerServiceAccountInfo body}
     * @return {@link BaseWeChatApiRes}
     * @since 2024/2/22
     */
    @HttpMapping(method = HttpMethod.POST, uri = "/customservice/kfaccount/add")
    BaseWeChatApiRes addCustomerServiceAccount(@WeChatAccessTokenParam String weChatIndex, @JsonBody CustomerServiceAccountInfo body);

    /**
     * 邀请绑定客服账号
     *
     * @param weChatIndexOrAccessToken {@link String weChatIndexOrAccessToken}
     * @param body                     {@link CustomerServiceAccountInfo body}
     * @return {@link BaseWeChatApiRes}
     * @since 2024/2/22
     */
    @HttpMapping(method = HttpMethod.POST, uri = "/customservice/kfaccount/inviteworker")
    BaseWeChatApiRes inviteWorker(@WeChatAccessTokenParam String weChatIndexOrAccessToken, @JsonBody CustomerServiceAccountInfo body);

    /**
     * 修改客服账号
     *
     * @param weChatIndexOrAccessToken {@link String weChatIndexOrAccessToken}
     * @param body                     {@link CustomerServiceAccountInfo body}
     * @return {@link BaseWeChatApiRes }
     * @since 2024/2/22
     */
    @HttpMapping(method = HttpMethod.POST, uri = "/customservice/kfaccount/update")
    BaseWeChatApiRes updateCustomerServiceAccount(@WeChatAccessTokenParam String weChatIndexOrAccessToken, @JsonBody CustomerServiceAccountInfo body);

    /**
     * 删除客服账号
     *
     * @param weChatIndexOrAccessToken {@link String weChatIndexOrAccessToken}
     * @param body                     {@link CustomerServiceAccountInfo body}
     * @return {@link BaseWeChatApiRes}
     * @since 2024/2/22
     */
    @HttpMapping(method = HttpMethod.POST, uri = "/customservice/kfaccount/del")
    BaseWeChatApiRes deleteCustomerServiceAccount(@WeChatAccessTokenParam String weChatIndexOrAccessToken, @JsonBody CustomerServiceAccountInfo body);

    /**
     * 删除客服账号
     *
     * @param weChatIndexOrAccessToken {@link String weChatIndexOrAccessToken}
     * @param kfAccount                {@link String kfAccount}
     * @return {@link BaseWeChatApiRes}
     * @since 2024/2/22
     */
    @HttpMapping(method = HttpMethod.GET, uri = "/customservice/kfaccount/del")
    BaseWeChatApiRes deleteCustomerServiceAccount(@WeChatAccessTokenParam String weChatIndexOrAccessToken, @HttpQuery(name = "kf_account") String kfAccount);


    /**
     * 上传客服头像
     *
     * @param weChatIndexOrAccessToken {@link String weChatIndexOrAccessToken}
     * @param file                     {@link MultipartFile file}
     * @return {@link BaseWeChatApiRes}
     * @since 2024/2/22
     */
    @HttpMapping(method = HttpMethod.POST, uri = "/customservice/kfaccount/uploadheadimg")
    BaseWeChatApiRes uploadHeadImg(@WeChatAccessTokenParam String weChatIndexOrAccessToken, @HttpQuery(name = "kf_account") String kfAccount, @FormData(name = "media") MultipartFile file);

    /**
     * 上传客服头像
     *
     * @param weChatIndexOrAccessToken {@link String weChatIndexOrAccessToken}
     * @param file                     {@link File file}
     * @return {@link BaseWeChatApiRes}
     * @since 2024/2/22
     */
    @HttpMapping(method = HttpMethod.POST, uri = "/customservice/kfaccount/uploadheadimg")
    BaseWeChatApiRes uploadHeadImg(@WeChatAccessTokenParam String weChatIndexOrAccessToken, @HttpQuery(name = "kf_account") String kfAccount, @FormData(name = "media") File file);

    /**
     * 获取所有客服账号
     *
     * @param weChatIndexOrAccessToken {@link String weChatIndexOrAccessToken}
     * @return {@link CustomerServiceAccountList}
     * @since 2024/2/22
     */
    @HttpMapping(method = HttpMethod.GET, uri = "/cgi-bin/customservice/getkflist")
    CustomerServiceAccountList getCustomerServiceAccountList(@WeChatAccessTokenParam String weChatIndexOrAccessToken);

    /**
     * 获取在线的客服列表
     *
     * @param weChatIndexOrAccessToken {@link String weChatIndexOrAccessToken}
     * @return {@link CustomerServiceOnLineAccountRes}
     * @since 2024/2/22
     */
    @HttpMapping(method = HttpMethod.GET, uri = "cgi-bin/customservice/getonlinekflist")
    CustomerServiceOnLineAccountRes getCustomerServiceOnLineAccountList(@WeChatAccessTokenParam String weChatIndexOrAccessToken);

    /**
     * 客服输入状态
     *
     * @param weChatIndexOrAccessToken {@link String weChatIndexOrAccessToken}
     * @param body                     {@link SendTypingReq body}
     * @return {@link BaseWeChatApiRes}
     * @since 2024/2/22
     */
    @HttpMapping(method = HttpMethod.GET, uri = "/cgi-bin/message/custom/typing")
    BaseWeChatApiRes typing(@WeChatAccessTokenParam String weChatIndexOrAccessToken, @JsonBody SendTypingReq body);


    /**
     * 创建客服会话
     *
     * @param weChatIndexOrAccessToken {@link String weChatIndexOrAccessToken}
     * @param body                     {@link CustomerServiceSessionReq body}
     * @return {@link BaseWeChatApiRes }
     * @since 2024/2/22
     */
    @HttpMapping(method = HttpMethod.POST, uri = "/customservice/kfsession/create")
    BaseWeChatApiRes createSession(@WeChatAccessTokenParam String weChatIndexOrAccessToken, @JsonBody CustomerServiceSessionReq body);

    /**
     * 关闭会话
     *
     * @param weChatIndexOrAccessToken {@link String weChatIndexOrAccessToken}
     * @param body                     {@link CustomerServiceSessionReq body}
     * @return {@link BaseWeChatApiRes }
     * @since 2024/2/22
     */
    @HttpMapping(method = HttpMethod.POST, uri = "/customservice/kfsession/close")
    BaseWeChatApiRes closeSession(@WeChatAccessTokenParam String weChatIndexOrAccessToken, @JsonBody CustomerServiceSessionReq body);

    /**
     * 获取客户会话状态
     *
     * @param weChatIndexOrAccessToken {@link String weChatIndexOrAccessToken}
     * @param openid                   {@link String openid}
     * @return {@link CustomerServiceSessionStatus }
     * @since 2024/2/22
     */
    @HttpMapping(method = HttpMethod.GET, uri = "/customservice/kfsession/getsession")
    CustomerServiceSessionStatus sessionStatus(@WeChatAccessTokenParam String weChatIndexOrAccessToken, @HttpQuery(name = "openid") String openid);

    /**
     * 获取客服会话列表
     *
     * @param weChatIndexOrAccessToken {@link String weChatIndexOrAccessToken}
     * @param kfAccount                {@link String kfAccount}
     * @return {@link CustomerServiceSessionListRes }
     * @since 2024/2/22
     */
    @HttpMapping(method = HttpMethod.GET, uri = "/customservice/kfsession/getsessionlist")
    CustomerServiceSessionListRes sessionList(@WeChatAccessTokenParam String weChatIndexOrAccessToken, @HttpQuery(name = "kf_account") String kfAccount);

    /**
     * 获取未接入的会话列表
     *
     * @param weChatIndexOrAccessToken {@link String weChatIndexOrAccessToken}
     * @return {@link CustomerServiceWaitCaseRes }
     * @since 2024/2/22
     */
    @HttpMapping(method = HttpMethod.GET, uri = "/customservice/kfsession/getwaitcase")
    CustomerServiceWaitCaseRes waitCase(@WeChatAccessTokenParam String weChatIndexOrAccessToken);

    /**
     * 获取客服聊天记录
     *
     * @param weChatIndexOrAccessToken {@link String weChatIndexOrAccessToken}
     * @param req                      {@link GetMsgListReq req}
     * @return {@link GetMsgListRes }
     * @since 2024/2/22
     */
    @HttpMapping(method = HttpMethod.POST, uri = "/customservice/msgrecord/getmsglist")
    GetMsgListRes msgList(@WeChatAccessTokenParam String weChatIndexOrAccessToken, @JsonBody GetMsgListReq req);

    /**
     * 发送客服消息
     *
     * @param weChatIndexOrAccessToken {@link String weChatIndexOrAccessToken}
     * @param body                     {@link WeChatCustomerMessage body}
     * @return {@link BaseWeChatApiRes}
     * @since 2024/2/22
     */
    @HttpMapping(method = HttpMethod.POST, uri = "/cgi-bin/message/custom/send")
    BaseWeChatApiRes sendCustomerMsg(@WeChatAccessTokenParam String weChatIndexOrAccessToken, @JsonBody WeChatCustomerMessage<?> body);
}