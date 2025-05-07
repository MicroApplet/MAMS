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

package com.asialjim.microapplet.mams.channel.wechat.official.cors.cmd;

import com.asialjim.microapplet.common.application.App;
import com.asialjim.microapplet.common.cors.Cmd;
import com.asialjim.microapplet.mams.channel.base.ChlEncType;
import com.asialjim.microapplet.mams.channel.wechat.WeChatOfficialCons;
import com.asialjim.microapplet.mams.channel.wechat.official.domain.WeChatOfficialAppAgg;
import com.asialjim.microapplet.mams.channel.wechat.official.domain.WeChatOfficialCallbackMsg;
import com.asialjim.microapplet.mams.channel.wechat.official.infrastructure.adaptor.aes.AesException;
import com.asialjim.microapplet.mams.channel.wechat.official.infrastructure.adaptor.aes.WeChatOfficialMsgCrypt;
import com.asialjim.microapplet.mams.channel.wechat.official.infrastructure.adaptor.node.EmptyObjectNode;
import com.asialjim.microapplet.mams.channel.wechat.pojo.WeChatApp;
import com.asialjim.microapplet.remote.net.jackson.AbstractJacksonUtil;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.NullNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.Optional;

/**
 * 微信公众号回调结果
 *
 * @author <a href="mailto:asialjim@hotmail.com">Asial Jim</a>
 * @version 1.0
 * @since 2025/4/27, &nbsp;&nbsp; <em>version:1.0</em>
 */
@Getter
@RequiredArgsConstructor
public class WeChatOfficialCallbackMsgOptCmd implements Cmd<String> {
    private static final Logger log = LoggerFactory.getLogger("WeChatOfficialMsgCallBackProcessor");
    private final String appid;
    private final String signature;
    private final String timestamp;
    private final String nonce;
    private final String openid;
    private final String encrypt_type;
    private final String msg_signature;
    private final HttpServletRequest request;

    @Setter
    private WeChatOfficialAppAgg weChatOfficialAppAgg;

    /**
     * 原文消息
     */
    @Setter
    private String msg;

    /**
     * 明文消息
     */
    @Setter
    private JsonNode plainTextMsg;


    public static WeChatOfficialCallbackMsgOptCmd cmd(
            String appid,
            String signature,
            String timestamp,
            String nonce,
            String openid,
            String encrypt_type,
            String msg_signature,
            HttpServletRequest request) {

        WeChatOfficialCallbackMsgOptCmd cmd = new WeChatOfficialCallbackMsgOptCmd(appid, signature, timestamp, nonce, openid, encrypt_type, msg_signature, request);
        App.beanOpt(WeChatOfficialAppAgg.class).ifPresent(item -> cmd.setWeChatOfficialAppAgg(item.withAppid(appid)));
        try {
            ServletInputStream inputStream = request.getInputStream();
            String characterEncoding = request.getCharacterEncoding();
            Charset charset = StringUtils.isNotBlank(characterEncoding) ? Charset.forName(characterEncoding) : StandardCharsets.UTF_8;
            String body = IOUtils.toString(inputStream, charset);
            cmd.setMsg(body);
        } catch (Throwable t) {
            cmd.setMsg(StringUtils.EMPTY);
        }
        return cmd;
    }

    @Override
    public String execute() {
        try {
            // 公众号聚合根
            WeChatOfficialAppAgg appAgg = getWeChatOfficialAppAgg();

            // 消息加解密工具
            WeChatOfficialMsgCrypt msgCrypt = appAgg.msgCrypt();

            // 加密通信模式
            ChlEncType wxEncType = Optional.of(appAgg)
                    .map(WeChatOfficialAppAgg::weChatApp)
                    .map(WeChatApp::getEncType)
                    .orElse(ChlEncType.WeChatOfficialMixing);

            String xml = ChlEncType.WeChatOfficialPlainText.equals(wxEncType)
                    ? getMsg()  // 明文模式
                    : msgCrypt.decryptMsg(getMsg_signature(), getTimestamp(), getNonce(), getMsg());  // 密文模式

            this.plainTextMsg = AbstractJacksonUtil.readXmlTree(xml);

            // 空消息
            if (this.plainTextMsg instanceof NullNode) {
                log.error("公众号消息：{} 反序列化异常", getMsg());
                return "success";
            }

            WeChatOfficialCallbackMsg callBackMsg = new WeChatOfficialCallbackMsg(this.plainTextMsg);
            ObjectNode callback = this.weChatOfficialAppAgg.callback(callBackMsg);
            if (Objects.isNull(callback) || callback instanceof EmptyObjectNode)
                return "success";

            //noinspection deprecation
            callback.put(WeChatOfficialCons.XmlMsgTag.toUserName, callBackMsg.fromUserName());
            //noinspection deprecation
            callback.put(WeChatOfficialCons.XmlMsgTag.fromUserName, callBackMsg.toUserName());
            //noinspection deprecation
            callback.put(WeChatOfficialCons.XmlMsgTag.createTime, callBackMsg.createTime());
            //noinspection deprecation
            callback.put(WeChatOfficialCons.XmlMsgTag.msgId, callBackMsg.msgId());

            String xmlRes = AbstractJacksonUtil.writeValueAsXmlString(callback);
            return ChlEncType.WeChatOfficialPlainText.equals(wxEncType)
                    ? xmlRes                                                    // 明文模式
                    : msgCrypt.encryptMsg(xmlRes, getTimestamp(), getNonce());  // 密文模式
        } catch (AesException e) {
            log.error("公众号消息：{} 解密异常：{}", getMsg(), e.getMessage(), e);
            return "success";
        }
    }
}