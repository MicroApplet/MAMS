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

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Arrays;

/**
 * 微信 SHA1 签名
 *
 * @author Asial Jim
 * @version 1.0
 * @since 2026/6/24, &nbsp;&nbsp; <em>version:1.0</em>
 */
public class SHA1 {
    public static String getSHA1(String... args) throws AesException {
        try {
            String[] array = args.clone();
            Arrays.sort(array);
            StringBuilder sb = new StringBuilder();
            for (String s : array) sb.append(s);

            MessageDigest md = MessageDigest.getInstance("SHA-1");
            md.update(sb.toString().getBytes(StandardCharsets.UTF_8));
            byte[] digest = md.digest();

            StringBuilder hexStrBuilder = new StringBuilder();
            for (byte b : digest) {
                String shaHex = Integer.toHexString(b & 0xFF);
                if (shaHex.length() < 2) hexStrBuilder.append(0);
                hexStrBuilder.append(shaHex);
            }
            return hexStrBuilder.toString();
        } catch (Exception e) {
            throw new AesException(AesException.ComputeSignatureError);
        }
    }
}
