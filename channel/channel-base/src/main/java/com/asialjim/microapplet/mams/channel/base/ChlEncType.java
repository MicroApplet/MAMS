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

package com.asialjim.microapplet.mams.channel.base;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;

/**
 * 渠道加密方式
 *
 * @author <a href="mailto:asialjim@hotmail.com">Asial Jim</a>
 * @version 1.0
 * @since 2025/4/10, &nbsp;&nbsp; <em>version:1.0</em>
 */
@Getter
@AllArgsConstructor
public enum ChlEncType {
    WeChatOfficialPlainText("wx:official:PLAINTEXT", "明文", ChlType.WeChat, ChlAppType.WeChatOfficial),
    WeChatOfficialCipherText("wx:official:CIPHERTEXT", "密文", ChlType.WeChat, ChlAppType.WeChatOfficial),
    WeChatOfficialMixing("wx:official:MIXING", "混合模式", ChlType.WeChat, ChlAppType.WeChatOfficial);

    private final String code;
    private final String desc;
    private final ChlType chlType;
    private final ChlAppType chlAppType;

    public static ChlEncType codeOf(String chlEncType) {
        return Arrays.stream(values()).filter(item -> StringUtils.equals(item.getCode(), chlEncType)).findAny().orElse(WeChatOfficialMixing);
    }
}