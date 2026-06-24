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

package com.asialjim.microapplet.mams.chl.wx.core.context;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Objects;

/**
 * 微信接口错误码
 *
 * @author Asial Jim
 * @version 1.0
 * @since 2026/6/24, &nbsp;&nbsp; <em>version:1.0</em>
 */
@Getter
@AllArgsConstructor
public enum WeChatApiResultEnumeration {
    OK(0, "请求成功"),
    CODE_40001(40001, "access_token 无效或 appsecret 错误"),
    CODE_40014(40014, "access_token 无效"),
    CODE_42001(42001, "access_token 超时"),
    UNKNOWN(-1, "未知错误");

    private final Integer code;
    private final String desc;

    public static WeChatApiResultEnumeration codeOf(Integer code) {
        if (Objects.isNull(code)) return OK;
        return Arrays.stream(values())
                .filter(item -> Objects.equals(item.getCode(), code))
                .findFirst()
                .orElse(UNKNOWN);
    }
}
