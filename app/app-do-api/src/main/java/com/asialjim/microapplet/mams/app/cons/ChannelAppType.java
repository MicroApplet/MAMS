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

package com.asialjim.microapplet.mams.app.cons;


import com.asialjim.microapplet.mams.app.context.ChlRs;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.commons.collections4.MultiValuedMap;
import org.apache.commons.collections4.multimap.ArrayListValuedHashMap;
import org.apache.commons.collections4.multimap.UnmodifiableMultiValuedMap;

import java.util.*;

/**
 * 渠道应用类型
 *
 * @author <a href="mailto:asialjim@hotmail.com">Asial Jim</a>
 * @version 1.0
 * @since 2025/8/29, &nbsp;&nbsp; <em>version:1.0</em>
 */
@Getter
@AllArgsConstructor
public enum ChannelAppType {

    WeChatApplet("APPLET", ChannelType.WeChat, "微信小程序"),
    WeChatMp("OFFICIAL", ChannelType.WeChat, "微信公众号"),
    WeChatEnt("ENTERPRISE", ChannelType.WeChat, "微信企业号"),
    WeChatMicTencent("MIC_TENCENT", ChannelType.WeChat, "腾讯小微"),
    WeChatGame("GAME", ChannelType.WeChat, "小游戏"),
    WeChatAIConversation("AI_CONVERSATION", ChannelType.WeChat, "智能对话"),
    WeChatPay("PAY", ChannelType.WeChat, "微信支付"),
    WeChatMall("MALL", ChannelType.WeChat, "小商店"),
    WeChatPlatform("PLATFORM", ChannelType.WeChat, "开放平台"),
    WeChatVideo("VIDEO", ChannelType.WeChat, "视频号"),
    WEB("WEB",ChannelType.PC,"站点网页"),
    CMS("CMS",ChannelType.PC,"后台管理"),
    H5("H5",ChannelType.H5,"H5");

    /**
     * 渠道应用编号
     */
    private final String code;
    /**
     * 渠道类型
     */
    private final ChannelType type;
    /**
     * 渠道应用描述
     */
    private final String desc;

    /**
     * CHL应用类型代码
     *
     * @return {@link String}
     */
    public String chlAppTypeCode() {
        return getType().getCode() + ":" + getCode();
    }

    private static final Map<String, ChannelAppType> CHL_APP_TYPE_CODE_MAP;
    private static final Map<String, ChannelAppType> CODE_MAP;
    private static final MultiValuedMap<ChannelType, ChannelAppType> TYPE_MAP;

    static {
        Map<String, ChannelAppType> chlAppTypeCodeMap = new HashMap<>();
        Map<String, ChannelAppType> codeMap = new HashMap<>();
        MultiValuedMap<ChannelType, ChannelAppType> typeMap = new ArrayListValuedHashMap<>();

        for (ChannelAppType value : values()) {
            chlAppTypeCodeMap.put(value.chlAppTypeCode(), value);
            codeMap.put(value.getCode(), value);
            typeMap.put(value.getType(), value);
        }

        CHL_APP_TYPE_CODE_MAP = Collections.unmodifiableMap(chlAppTypeCodeMap);
        CODE_MAP = Collections.unmodifiableMap(codeMap);
        TYPE_MAP = UnmodifiableMultiValuedMap.unmodifiableMultiValuedMap(typeMap);
    }

    /**
     * 的应用程序类型代码
     *
     * @param code 代码
     * @return {@link ChannelAppType}
     */
    public static ChannelAppType chlAppTypeCodeOf(String code) {
        return Optional.of(CHL_APP_TYPE_CODE_MAP)
                .map(item -> item.get(code))
                .orElseThrow(ChlRs.NoSuchChlAppType::ex);
    }

    /**
     * 代码的
     *
     * @param code 代码
     * @return {@link ChannelAppType}
     */
    public static ChannelAppType codeOf(String code) {
        return Optional.of(CODE_MAP)
                .map(item -> item.get(code))
                .orElseThrow(ChlRs.NoSuchChlAppType::ex);
    }


    /**
     * 查询指定渠道支持的所有渠道应用类型
     *
     * @param chlCode {@link String chlCode}
     * @return {@link List<ChannelAppType> }
     * @since 2025/8/29
     */
    public static List<ChannelAppType> typesOfChl(String chlCode) {
        ChannelType type = ChannelType.codeOf(chlCode);
        Collection<ChannelAppType> types = Optional.of(TYPE_MAP)
                .map(item -> item.get(type))
                .orElseThrow(ChlRs.ChlTypeHasNoAppType::ex);
        return new ArrayList<>(types);
    }
}