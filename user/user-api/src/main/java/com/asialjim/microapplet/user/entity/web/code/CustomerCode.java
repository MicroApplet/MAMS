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

package com.asialjim.microapplet.user.entity.web.code;

import com.asialjim.microapplet.commons.standard.context.ResCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 用户响应码
 * 取值范围: 900200 -> 900500
 *
 * @author Asial Jim
 * @version 1.0
 * @since 2026/3/6, &nbsp;&nbsp; <em>version:1.0</em>
 */
@Getter
@AllArgsConstructor
public enum CustomerCode implements ResCode {
    ExistMoreThan1MainUserId(200,false,"900200","用户主账户冲突，请联系行员处理"),
    RegisterMainUserParamErr(200,false,"900201","注册主用户参数错误"),
    RegisterMainUserFailure(200,false,"900202","注册主用户失败"),
    RegisterChlUserFailure(200,false,"900203","注册用户失败"),
    RegisterChlUserParamErr(200,false,"900204","注册用户参数错误"),

    // 占位
    OK(200,true,"000000","成功");

    private final int status;
    private final boolean success;
    private final String code;
    private final String msg;

}
