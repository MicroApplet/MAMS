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

package com.asialjim.microapplet.mams.callback.wx.mp.crypto;

import com.asialjim.microapplet.commons.standard.exception.BusinessException;
import com.asialjim.microapplet.mams.chl.wx.core.application.WeChatApplication;
import com.asialjim.microapplet.mams.chl.wx.core.application.WeChatApplicationRepository;
import com.asialjim.microapplet.mams.chl.wx.core.code.WeChatResCode;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 微信公众号消息加解密服务
 *
 * @author Asial Jim
 * @version 1.0
 * @since 2026/6/24, &nbsp;&nbsp; <em>version:1.0</em>
 */
@Service
@RequiredArgsConstructor
public class WeChatOfficialMsgCryptService {
    private final WeChatApplicationRepository applicationRepository;
    private final Map<String, WeChatOfficialMsgCrypt> crypts = new ConcurrentHashMap<>();

    public String verify(String appid, String signature, String timestamp, String nonce, String echostr) {
        try {
            return cryptOf(appid).verifyUrl(signature, timestamp, nonce, echostr);
        } catch (AesException e) {
            throw new BusinessException(401, String.valueOf(e.getCode()), e.getMessage(), null, null);
        }
    }

    public String decryptMsg(String appid, String msgSignature, String timestamp, String nonce, String xml, AtomicBoolean enableCipher) {
        try {
            return cryptOf(appid).decryptMsg(msgSignature, timestamp, nonce, xml, enableCipher);
        } catch (AesException e) {
            throw new BusinessException(401, String.valueOf(e.getCode()), e.getMessage(), null, null);
        }
    }

    public String encryptMsg(String appid, String replyXml, String timestamp, String nonce) {
        try {
            return cryptOf(appid).encryptMsg(replyXml, timestamp, nonce);
        } catch (AesException e) {
            throw new BusinessException(500, String.valueOf(e.getCode()), e.getMessage(), null, null);
        }
    }

    private WeChatOfficialMsgCrypt cryptOf(String appid) {
        return crypts.computeIfAbsent(appid, key -> {
            WeChatApplication app = applicationRepository.resolve(key);
            if (StringUtils.isAnyBlank(app.getToken(), app.getAesKey(), app.getAppid()))
                throw WeChatResCode.AppSecretMissing.exWithData("微信公众号 token/aesKey/appid 缺失");
            try {
                return new WeChatOfficialMsgCrypt(app.getToken(), app.getAesKey(), app.getAppid());
            } catch (AesException e) {
                throw new BusinessException(500, String.valueOf(e.getCode()), e.getMessage(), app, null);
            }
        });
    }
}
