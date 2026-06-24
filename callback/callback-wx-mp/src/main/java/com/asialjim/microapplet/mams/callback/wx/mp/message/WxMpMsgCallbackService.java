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

package com.asialjim.microapplet.mams.callback.wx.mp.message;

import com.asialjim.microapplet.mams.callback.wx.mp.crypto.WeChatOfficialMsgCryptService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 微信公众号消息回调服务
 *
 * @author Asial Jim
 * @version 1.0
 * @since 2026/6/24, &nbsp;&nbsp; <em>version:1.0</em>
 */
@Service
@RequiredArgsConstructor
public class WxMpMsgCallbackService {
    private final WeChatOfficialMsgCryptService cryptService;
    private final List<CallbackMsgHandler> handlers;

    public String post(String appid,
                       String signature,
                       String timestamp,
                       String nonce,
                       String openid,
                       String encryptType,
                       String msgSignature,
                       String xml) {
        AtomicBoolean cipher = new AtomicBoolean(false);
        String decryptXml = xml;
        if ("aes".equals(encryptType))
            decryptXml = cryptService.decryptMsg(appid, msgSignature, timestamp, nonce, xml, cipher);

        WeChatOfficialMsgCallbackEvent event = new WeChatOfficialMsgCallbackEvent()
                .setAppid(appid)
                .setSignature(signature)
                .setTimestamp(timestamp)
                .setNonce(nonce)
                .setOpenid(openid)
                .setEncryptType(encryptType)
                .setMsgSignature(msgSignature)
                .setSourceXml(xml)
                .setDecryptXml(decryptXml)
                .init();

        Optional<WxMpXmlOutMessage> message = handlers.stream()
                .filter(handler -> handler.support(event))
                .map(handler -> handler.handle(event))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .findFirst();

        if (message.isEmpty()) return "success";

        String reply = message.get().toXml();
        if (cipher.get())
            return cryptService.encryptMsg(appid, reply, timestamp, nonce);
        return reply;
    }
}
