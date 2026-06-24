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

import lombok.Getter;

/**
 * 微信 AES 异常
 *
 * @author Asial Jim
 * @version 1.0
 * @since 2026/6/24, &nbsp;&nbsp; <em>version:1.0</em>
 */
@Getter
public class AesException extends Exception {
    public static final int OK = 0;
    public static final int ValidateSignatureError = -40001;
    public static final int ParseXmlError = -40002;
    public static final int ComputeSignatureError = -40003;
    public static final int IllegalAesKey = -40004;
    public static final int ValidateAppidError = -40005;
    public static final int EncryptAESError = -40006;
    public static final int DecryptAESError = -40007;
    public static final int IllegalBuffer = -40008;
    public static final int EncodeBase64Error = -40009;
    public static final int DecodeBase64Error = -40010;
    public static final int GenReturnXmlError = -40011;

    private final int code;

    public AesException(int code) {
        super(message(code));
        this.code = code;
    }

    private static String message(int code) {
        return switch (code) {
            case ValidateSignatureError -> "签名验证错误";
            case ParseXmlError -> "xml解析失败";
            case ComputeSignatureError -> "sha加密生成签名失败";
            case IllegalAesKey -> "SymmetricKey非法";
            case ValidateAppidError -> "appid校验失败";
            case EncryptAESError -> "aes加密失败";
            case DecryptAESError -> "aes解密失败";
            case IllegalBuffer -> "解密后得到的buffer非法";
            case EncodeBase64Error -> "base64加密错误";
            case DecodeBase64Error -> "base64解密错误";
            case GenReturnXmlError -> "xml生成失败";
            default -> "微信 AES 异常";
        };
    }
}
