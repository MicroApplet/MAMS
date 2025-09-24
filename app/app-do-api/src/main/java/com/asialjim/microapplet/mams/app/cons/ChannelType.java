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
import com.asialjim.microapplet.mams.app.vo.ChannelTypeVo;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * 渠道类型
 *
 * @author <a href="mailto:asialjim@hotmail.com">Asial Jim</a>
 * @version 1.0
 * @since 2025/8/29, &nbsp;&nbsp; <em>version:1.0</em>
 */
@Getter
@AllArgsConstructor
public enum ChannelType {
    PC("PC", "PC渠道"),
    WeChat("wechat", "微信渠道"),
    H5("H5", "H5");

    private final String code;
    private final String desc;

    public static final Map<String, ChannelType> CODE_MAP;
    public static final Map<String, ChannelType> NAME_MAP;

    static {
        Map<String, ChannelType> codeMap = new HashMap<>();
        Map<String, ChannelType> nameMap = new HashMap<>();

        for (ChannelType value : values()) {
            codeMap.put(value.getCode(), value);
            nameMap.put(value.name(), value);
        }

        CODE_MAP = Collections.unmodifiableMap(codeMap);
        NAME_MAP = Collections.unmodifiableMap(nameMap);
    }

    /**
     * 代码的
     *
     * @param code 代码
     * @return {@link ChannelType}
     */
    public static ChannelType codeOf(String code) {
        return Optional.of(CODE_MAP)
                .map(item -> item.get(code))
                .orElseThrow(ChlRs.ChlUnSupportEx::ex);
    }

    /**
     * 的名字
     *
     * @param name 名字
     * @return {@link ChannelType}
     */
    public static ChannelType nameOf(String name) {
        return Optional.of(NAME_MAP)
                .map(item -> item.get(name))
                .orElseThrow(ChlRs.ChlUnSupportEx::ex);
    }

    /**
     * 签证官
     *
     * @return {@link ChannelTypeVo}
     */
    public ChannelTypeVo vo(){
        ChannelTypeVo vo = new ChannelTypeVo();
        vo.setCode(getCode());
        vo.setDesc(getDesc());
        return vo;
    }
}