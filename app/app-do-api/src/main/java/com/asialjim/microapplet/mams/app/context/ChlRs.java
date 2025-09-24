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

package com.asialjim.microapplet.mams.app.context;

import com.asialjim.microapplet.common.context.ResCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 渠道错误代码
 *
 * @author <a href="mailto:asialjim@hotmail.com">Asial Jim</a>
 * @version 1.0
 * @since 2025/8/29, &nbsp;&nbsp; <em>version:</em>
 */
@Getter
@AllArgsConstructor
public enum ChlRs implements ResCode {
    LoginChlIllegal(400, true, "LOGIN:CHL:ILLEGAL", "非法登录渠道"),
    IllegalLoginChlAppType(400, true, "LOGIN:CHL-APP-TYPE:ILLEGAL", "非法登录渠道应用类型"),
    ChlUnSupportEx(400, true, "CHL:UN-SUPPORT:EX", "不支持的请求渠道"),
    NoSuchChlApp(400, true, "CHL:APP:NO-SUCH", "没有该渠道应用"),
    NoSuchChlAppType(400, true, "CHL:APP-TYPE:NO-SUCH", "没有该渠道应用类型"),
    ChlTypeHasNoAppType(400, true, "CHL:APP-TYPE:EMPTY", "没有该渠道应用类型");

    private final int status;
    private final boolean thr;
    private final String code;
    private final String msg;
}