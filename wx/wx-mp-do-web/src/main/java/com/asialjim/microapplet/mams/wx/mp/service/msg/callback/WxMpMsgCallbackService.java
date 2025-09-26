/*
 *    Copyright 2014-2025 <a href="mailto:asialjim@qq.com">Asial Jim</a>
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package com.asialjim.microapplet.mams.wx.mp.service.msg.callback;

import com.asialjim.microapplet.common.event.EventUtil;
import com.asialjim.microapplet.common.utils.XmlUtil;
import com.asialjim.microapplet.wechat.application.WeChatApplication;
import com.asialjim.microapplet.wechat.application.WeChatApplicationRepository;
import com.asialjim.microapplet.wechat.encryt.official.WeChatOfficialMsgCryptService;
import com.asialjim.microapplet.wechat.encryt.official.aes.WeChatOfficialMsgCrypt;
import com.asialjim.microapplet.wechat.official.module.message.CallbackMsgHandler;
import com.asialjim.microapplet.wechat.official.module.message.WeChatOfficialMsgCallbackEvent;
import com.asialjim.microapplet.wechat.official.module.message.reply.WeChatOutMsgListEvent;
import com.asialjim.microapplet.wechat.official.module.message.reply.WxMpXmlOutMessage;
import com.fasterxml.jackson.databind.JsonNode;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;

@Slf4j
@Component
public class WxMpMsgCallbackService {

    @Resource
    private WeChatApplicationRepository.Aggregator aggregator;
    @Resource
    private WeChatOfficialMsgCryptService weChatOfficialMsgCryptService;
    @Resource
    private List<CallbackMsgHandler> callbackMsgHandlers;


    public ResponseEntity<String> post(String appid,
                                       String signature,
                                       String timestamp,
                                       String nonce,
                                       String openid,
                                       String encrypt_type,
                                       String msg_signature,
                                       RequestEntity<String> requestEntity) {

        String res = "success";

        AtomicBoolean enableCipher = new AtomicBoolean();
        WeChatApplication app = this.aggregator.appByIndex(appid);
        WeChatOfficialMsgCrypt crypt = this.weChatOfficialMsgCryptService.cryptOf(app);
        String body = requestEntity.getBody();
        String xml;
        try {
            xml = crypt.decryptMsg(signature, timestamp, nonce, body, enableCipher);

        } catch (Throwable t) {
            log.warn("微信消息：{} 解密失败：{}", body, t.getMessage(), t);
            return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.TEXT_PLAIN).body(res);
        }

        JsonNode msgNode = XmlUtil.instance.toBean(xml, JsonNode.class);
        WeChatOfficialMsgCallbackEvent msgCallBackEvent = new WeChatOfficialMsgCallbackEvent()
                .setAppid(appid)
                .setSignature(signature)
                .setTimestamp(timestamp)
                .setNonce(nonce)
                .setOpenid(openid)
                .setEncrypt_type(encrypt_type)
                .setMsg_signature(msg_signature)
                .setSourceXml(body)
                .setDecryptXml(xml)
                .setMsgNode(msgNode)
                .init();

        List<WxMpXmlOutMessage> list = callbackMsgHandlers.stream()
                .filter(item -> item.support(msgCallBackEvent.getMsgType()))
                .map(item -> item.handle(msgCallBackEvent))
                .toList();

        // 多个回复
        if (CollectionUtils.size(list) > 1) {
            EventUtil.push(new WeChatOutMsgListEvent(list));
        }
        // 一个回复或者没有回复
        else {
            WxMpXmlOutMessage wxMpXmlOutMessage = list.stream().filter(Objects::nonNull).findAny().orElse(null);
            if (Objects.nonNull(wxMpXmlOutMessage)) {
                wxMpXmlOutMessage.setCreateTime(NumberUtils.toLong(msgCallBackEvent.nodeTextValue("CreateTime")));
                wxMpXmlOutMessage.setFromUserName(msgCallBackEvent.nodeTextValue("ToUserName"));
                wxMpXmlOutMessage.setToUserName(msgCallBackEvent.nodeTextValue("FromUserName"));
                res = XmlUtil.instance.toStr(wxMpXmlOutMessage);
                if (enableCipher.get())
                    try {
                        res = crypt.encryptMsg(res, timestamp, nonce);
                    } catch (Throwable t) {
                        log.warn("加密响应消息：{} 异常：{}", res, t.getMessage(), t);
                    }
            }
        }

        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.TEXT_PLAIN).body(res);
    }
}