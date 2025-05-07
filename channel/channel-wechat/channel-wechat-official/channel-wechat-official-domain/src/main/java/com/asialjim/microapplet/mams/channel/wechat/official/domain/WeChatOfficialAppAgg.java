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

package com.asialjim.microapplet.mams.channel.wechat.official.domain;

import com.asialjim.microapplet.common.application.App;
import com.asialjim.microapplet.mams.channel.base.ChlAppType;
import com.asialjim.microapplet.mams.channel.wechat.domain.WeChatAppAgg;
import com.asialjim.microapplet.mams.channel.wechat.infrastructure.adaptor.WeChatApiResultEnumeration;
import com.asialjim.microapplet.mams.channel.wechat.official.cors.cmd.WeChatOfficialCallbackUrlCheckCmd;
import com.asialjim.microapplet.mams.channel.wechat.official.infrastructure.adaptor.aes.WeChatOfficialMsgCrypt;
import com.asialjim.microapplet.mams.channel.wechat.official.infrastructure.adaptor.callback.CallbackMsgProcessorContext;
import com.asialjim.microapplet.mams.channel.wechat.official.infrastructure.adaptor.callback.ai.CallbackMsgAIContext;
import com.asialjim.microapplet.mams.channel.wechat.official.infrastructure.remoting.qrcode.WeChatOfficialQrCodeRemoting;
import com.asialjim.microapplet.mams.channel.wechat.official.infrastructure.remoting.qrcode.meta.CreateQrCodeResponse;
import com.asialjim.microapplet.mams.channel.wechat.official.infrastructure.repository.WeChatOfficialCallbackAISessionManager;
import com.asialjim.microapplet.mams.channel.wechat.official.pojo.QrCode;
import com.asialjim.microapplet.mams.channel.wechat.pojo.WeChatApp;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.Duration;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Supplier;

/**
 * 微信公众号应用聚合根
 *
 * @author <a href="mailto:asialjim@hotmail.com">Asial Jim</a>
 * @version 1.0
 * @since 2025/4/27, &nbsp;&nbsp; <em>version:1.0</em>
 */
@Slf4j
@Setter
@Component
@Scope("request")
public class WeChatOfficialAppAgg {
    private WeChatAppAgg weChatAppAgg;
    private WeChatOfficialMsgCrypt msgCrypt;
    @Resource
    private CallbackMsgProcessorContext callbackMsgProcessorContext;
    @Resource
    private WeChatOfficialQrCodeRemoting weChatOfficialQrCodeRemoting;
    @Resource
    private WeChatOfficialCallbackAISessionManager wechatOfficialCallbackAISessionManager;
    @Resource
    private CallbackMsgAIContext callbackMsgAIContext;


    public WeChatOfficialAppAgg withAppid(String appid) {
        Optional<WeChatAppAgg> opt = App.beanOpt(WeChatAppAgg.class);
        opt.ifPresent(item -> setWeChatAppAgg(item.withId(appid)));
        return this;
    }

    private WeChatApp weChatApp;


    /**
     * 获取对应的公众号应用信息
     *
     * @return {@link WeChatApp}
     * @since 2025/4/29
     */
    public WeChatApp weChatApp() {
        if (Objects.nonNull(weChatApp))
            return weChatApp;

        weChatApp = this.weChatAppAgg.weChatApp();
        if (Objects.isNull(weChatApp))
            throw WeChatApiResultEnumeration.CODE_40013.sysException();
        ChlAppType appType = weChatApp.getAppType();

        if (!ChlAppType.WeChatOfficial.equals(appType))
            WeChatApiResultEnumeration.CODE_40013.throwBiz();

        return weChatApp;
    }

    /**
     * 公众号消息加解密处理器
     *
     * @return {@link WeChatOfficialMsgCrypt }
     * @since 2025/4/29
     */
    public WeChatOfficialMsgCrypt msgCrypt() {
        if (Objects.nonNull(msgCrypt))
            return msgCrypt;

        try {
            WeChatApp weChatApp = weChatApp();
            this.msgCrypt = new WeChatOfficialMsgCrypt(weChatApp.getToken(), weChatApp.getAesKey(), weChatApp.getAppid());
            return this.msgCrypt;
        } catch (Throwable t) {
            throw WeChatApiResultEnumeration.CODE_40013.sysException();
        }
    }

    /**
     * 开发者服务器认证
     *
     * @param cmd {@link WeChatOfficialCallbackUrlCheckCmd cmd}
     * @return {@link String }
     * @since 2025/4/29
     */
    public String callbackUrlCheck(WeChatOfficialCallbackUrlCheckCmd cmd) {
        try {
            String decrypt = msgCrypt().verifyUrl(cmd.getSignature(), cmd.getTimestamp(), cmd.getNonce(), cmd.getEchostr());
            log.info("开发者服务器认证结果：{}", decrypt);
            return cmd.getEchostr();
        } catch (Throwable t) {
            return "false";
        }
    }

    /**
     * 公众号消息回调处理
     *
     * @param callBackMsg {@link WeChatOfficialCallbackMsg 消息}
     * @return {@link JsonNode }
     * @since 2025/4/29
     */
    public ObjectNode callback(WeChatOfficialCallbackMsg callBackMsg) {
        // AI 模式
        ObjectNode aiRes = this.callbackMsgAIContext.aiRoute(callBackMsg);
        if (Objects.nonNull(aiRes))
            return aiRes;

        // 非AI模式
        return this.callbackMsgProcessorContext.process(callBackMsg);
    }


    /**
     * 创建临时微信公众号二维码
     *
     * @param scene {@link String scene}
     * @return {@link QrCode }
     */
    public QrCode create(String scene, Duration timeout) {
        WeChatApp weChatApp = weChatApp();
        CreateQrCodeResponse createQrCodeResponse = this.weChatOfficialQrCodeRemoting.create(weChatApp.getAppid(), scene, timeout);
        return QrCode.createQrCode(createQrCodeResponse);
    }

    /**
     * 创建临时微信公众号二维码
     *
     * @param scene {@link Integer scene}
     * @return {@link QrCode }
     */
    public QrCode create(Integer scene, Duration timeout) {
        WeChatApp weChatApp = weChatApp();
        CreateQrCodeResponse createQrCodeResponse = this.weChatOfficialQrCodeRemoting.create(weChatApp.getAppid(), scene, timeout);
        return QrCode.createQrCode(createQrCodeResponse);
    }

    /**
     * 创建永久二维码
     *
     * @param scene {@link String scene}
     * @return {@link QrCode }
     * @since 2025/4/29
     */
    public QrCode create(String scene) {
        WeChatApp weChatApp = weChatApp();
        CreateQrCodeResponse createQrCodeResponse = this.weChatOfficialQrCodeRemoting.create(weChatApp.getAppid(), scene);
        return QrCode.createQrCode(createQrCodeResponse);
    }

    /**
     * 创建永久二维码
     *
     * @param scene {@link Integer scene}
     * @return {@link QrCode }
     * @since 2025/4/29
     */
    public QrCode create(Integer scene) {
        WeChatApp weChatApp = weChatApp();
        CreateQrCodeResponse createQrCodeResponse = this.weChatOfficialQrCodeRemoting.create(weChatApp.getAppid(), scene);
        return QrCode.createQrCode(createQrCodeResponse);
    }

    /**
     * 授权网页登录
     *
     * @param code {@link String code}
     * @return {@link String }
     * @since 2025/4/29
     */
    public String pageLogin(String code) {

        // TODO 执行登录，并获取一个登录成功后的token
        return null;
    }

    /**
     * 执行授权网页连接处理
     *
     * @param scene {@link String scene}
     * @param state {@link String state}
     * @return {@link String }
     * @since 2025/4/29
     */
    public String page(String scene, String state) {
        // TODO 根据业务场景，路由到正确的授权连接业务处理器
        // TODO state 为业务标志
        // TODO state 决定最终的URL
        // TODO scene 决定执行哪些业务（业务场景）
        return null;
    }
}