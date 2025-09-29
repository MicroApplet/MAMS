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
 * 应用错误代码
 *
 * @author <a href="mailto:asialjim@hotmail.com">Asial Jim</a>
 * @version 1.0
 * @since 2025/8/29, &nbsp;&nbsp; <em>version:1.0</em>
 */
@Getter
@AllArgsConstructor
public enum AppRs implements ResCode {
    ChlAppNotSet(400,true,"CHL-APP:PARAMETER:MISS","渠道应用类型或编号未指定"),
    NoSuchApp(400, true, "APP:NOT-EXIST", "该应用不存在"),
    NoSuchChlApp(400, true, "CHL-APP:NOT-EXIST", "该渠道应用不存在"),
    RootExistMoreThanOne(400, true, "APP:ROOT-EXIST:MORE-THAN-ONE", "存在多个超管应用");

    private final int status;
    private final boolean thr;
    private final String code;
    private final String msg;

}