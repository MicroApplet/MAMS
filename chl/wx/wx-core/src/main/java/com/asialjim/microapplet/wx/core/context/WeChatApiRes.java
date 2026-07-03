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

package com.asialjim.microapplet.wx.core.context;

import com.asialjim.microapplet.wx.core.code.WeChatResCode;

import java.util.Objects;

/**
 * 微信接口响应
 *
 * @author Asial Jim
 * @version 1.0
 * @since 2026/6/24, &nbsp;&nbsp; <em>version:1.0</em>
 */
public interface WeChatApiRes {
    Integer getErrcode();

    String getErrmsg();

    default boolean success() {
        Integer errcode = getErrcode();
        return Objects.isNull(errcode) || errcode == 0;
    }

    static void check(WeChatApiRes res,String... errs){
        if (Objects.isNull(res))
            WeChatResCode.NoResponse.thr(errs);
        if (res.success()) {
            return;
        }
        WeChatApiResultEnumeration code = WeChatApiResultEnumeration.codeOf(res.getErrcode());
        code.thr(errs);
    }

    default boolean accessTokenInvalid() {
        WeChatApiResultEnumeration code = WeChatApiResultEnumeration.codeOf(getErrcode());
        return WeChatApiResultEnumeration.CODE_40001 == code
                || WeChatApiResultEnumeration.CODE_40014 == code
                || WeChatApiResultEnumeration.CODE_42001 == code;
    }
}
