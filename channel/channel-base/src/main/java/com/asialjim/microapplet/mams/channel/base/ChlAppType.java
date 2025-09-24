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
 * 渠道应用类型
 *
 * @author <a href="mailto:asialjim@hotmail.com">Asial Jim</a>
 * @version 1.0
 * @since 2025/4/10, &nbsp;&nbsp; <em>version:1.0</em>
 */
@Getter
@AllArgsConstructor
public enum ChlAppType {
    H5("H5", "H5平台、包括PC端", ChlType.H5),
    Phone("Phone", "移动电话渠道", ChlType.Phone),

    WeChatOfficial("wechat:official", "微信公众号", ChlType.WeChat),
    WeChatApplet("wechat:applet", "微信小程序", ChlType.WeChat);

    private final String code;
    private final String desc;
    private final ChlType chlType;

    public static ChlAppType codeOf(String code){
        return Arrays.stream(values()).filter(item -> StringUtils.equals(code, item.getCode()))
                .findAny()
                .orElseThrow(ChlAppTypeResCode.ChlAppTpUnknownEnum::ex);
    }
}