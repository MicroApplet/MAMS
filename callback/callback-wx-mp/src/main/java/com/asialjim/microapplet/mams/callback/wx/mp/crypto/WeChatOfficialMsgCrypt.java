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

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 微信公众号消息加解密
 *
 * @author Asial Jim
 * @version 1.0
 * @since 2026/6/24, &nbsp;&nbsp; <em>version:1.0</em>
 */
public class WeChatOfficialMsgCrypt {
    private static final Charset CHARSET = StandardCharsets.UTF_8;
    private static final String RANDOM_BASE = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

    private final byte[] aesKey;
    private final String token;
    private final String appId;

    public WeChatOfficialMsgCrypt(String token, String encodingAesKey, String appId) throws AesException {
        if (StringUtils.isNotBlank(encodingAesKey) && encodingAesKey.length() != 43)
            throw new AesException(AesException.IllegalAesKey);

        this.token = token;
        this.appId = appId;
        this.aesKey = Base64.decodeBase64(encodingAesKey + "=");
    }

    public String encryptMsg(String replyMsg, String timeStamp, String nonce) throws AesException {
        String encrypt = encrypt(randomStr(), replyMsg);
        if (StringUtils.isBlank(timeStamp)) timeStamp = Long.toString(System.currentTimeMillis());
        String signature = SHA1.getSHA1(token, timeStamp, nonce, encrypt);
        return XMLParse.generate(encrypt, signature, timeStamp, nonce);
    }

    public String decryptMsg(String msgSignature, String timeStamp, String nonce, String postData, AtomicBoolean enableCipher) throws AesException {
        String[] encrypt = XMLParse.extract(postData);
        String signature = SHA1.getSHA1(token, timeStamp, nonce, encrypt[1]);
        if (!signature.equals(msgSignature))
            throw new AesException(AesException.ValidateSignatureError);

        enableCipher.set(Boolean.parseBoolean(encrypt[0]));
        return enableCipher.get() ? decrypt(encrypt[1]) : postData;
    }

    public String verifyUrl(String msgSignature, String timeStamp, String nonce, String echoStr) throws AesException {
        String signature = SHA1.getSHA1(token, timeStamp, nonce);
        if (!signature.equals(msgSignature))
            throw new AesException(AesException.ValidateSignatureError);
        return echoStr;
    }

    private String encrypt(String randomStr, String text) throws AesException {
        ByteGroup byteCollector = new ByteGroup();
        byte[] randomStrBytes = randomStr.getBytes(CHARSET);
        byte[] textBytes = text.getBytes(CHARSET);
        byte[] networkBytesOrder = networkBytesOrder(textBytes.length);
        byte[] appidBytes = appId.getBytes(CHARSET);

        byte[] padBytes = PKCS7Encoder.encode(byteCollector.size() + appidBytes.length);
        byteCollector.addBytes(randomStrBytes)
                .addBytes(networkBytesOrder)
                .addBytes(textBytes)
                .addBytes(appidBytes)
                .addBytes(padBytes);

        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
            SecretKeySpec keySpec = new SecretKeySpec(aesKey, "AES");
            IvParameterSpec iv = new IvParameterSpec(aesKey, 0, 16);
            cipher.init(Cipher.ENCRYPT_MODE, keySpec, iv);
            return Base64.encodeBase64String(cipher.doFinal(byteCollector.toBytes()));
        } catch (Exception e) {
            throw new AesException(AesException.EncryptAESError);
        }
    }

    private String decrypt(String text) throws AesException {
        byte[] original;
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
            SecretKeySpec keySpec = new SecretKeySpec(aesKey, "AES");
            IvParameterSpec iv = new IvParameterSpec(Arrays.copyOfRange(aesKey, 0, 16));
            cipher.init(Cipher.DECRYPT_MODE, keySpec, iv);
            original = cipher.doFinal(Base64.decodeBase64(text));
        } catch (Exception e) {
            throw new AesException(AesException.DecryptAESError);
        }

        String xmlContent;
        String fromAppid;
        try {
            byte[] bytes = PKCS7Encoder.decode(original);
            byte[] networkOrder = Arrays.copyOfRange(bytes, 16, 20);
            int xmlLength = recoverNetworkBytesOrder(networkOrder);
            xmlContent = new String(Arrays.copyOfRange(bytes, 20, 20 + xmlLength), CHARSET);
            fromAppid = new String(Arrays.copyOfRange(bytes, 20 + xmlLength, bytes.length), CHARSET);
        } catch (Exception e) {
            throw new AesException(AesException.IllegalBuffer);
        }

        if (!fromAppid.equals(appId))
            throw new AesException(AesException.ValidateAppidError);
        return xmlContent;
    }

    private String randomStr() {
        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 16; i++) {
            sb.append(RANDOM_BASE.charAt(random.nextInt(RANDOM_BASE.length())));
        }
        return sb.toString();
    }

    private byte[] networkBytesOrder(int sourceNumber) {
        byte[] orderBytes = new byte[4];
        orderBytes[3] = (byte) (sourceNumber & 0xFF);
        orderBytes[2] = (byte) (sourceNumber >> 8 & 0xFF);
        orderBytes[1] = (byte) (sourceNumber >> 16 & 0xFF);
        orderBytes[0] = (byte) (sourceNumber >> 24);
        return orderBytes;
    }

    private int recoverNetworkBytesOrder(byte[] orderBytes) {
        int sourceNumber = 0;
        for (byte orderByte : orderBytes) {
            sourceNumber <<= 8;
            sourceNumber |= orderByte & 0xff;
        }
        return sourceNumber;
    }
}
