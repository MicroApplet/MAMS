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

package com.asialjim.microapplet.user.infrastructure.util;


import com.asialjim.microapplet.commons.chl.PlatformType;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public abstract class UserIdGenerator {
    private static final String SHA_256 = "SHA-256";

    /**
     * 生成用户编号
     * <p/>
     * 生成方案：
     * <p/>
     * <ul>
     *     <li>{渠道短编码}@{appid}:{sha256(appid+openid)}</li>
     *     <li>{渠道短编码}@{platformId}:{sha256(platformId+unionid)}</li>
     * </ul>
     *
     * @param type {@link PlatformType 渠道类型}
     * @param id   {@link String appid/platformId}
     * @param code {@link String openid/unionid}
     * @return {@link String user code}
     */
    public static String generator(PlatformType type, String id, String code) {
        String input = id + code;
        String hashPart =  sha256WithBase64Url(input);
        return type.getShortCode() + "@" + id + ":" + hashPart;
    }

    public static String sha256WithBase64Url(String input){
        byte[] inputBytes = input.getBytes(StandardCharsets.UTF_8);

        byte[] hash;
        try {
            MessageDigest md = MessageDigest.getInstance(SHA_256);
            hash = md.digest(inputBytes);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 not available");
        }

        return Base64.getUrlEncoder().withoutPadding().encodeToString(hash);
    }
}
