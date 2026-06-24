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
 * 客户辅助认证相关的业务代码
 * 取值范围: 900100 -> 900199
 *
 * @author Asial Jim
 * @version 1.0
 * @since 2026/3/6, &nbsp;&nbsp; <em>version:1.0</em>
 */
@Getter
@AllArgsConstructor
public enum CustomerSecondaryAuthenticationCode implements ResCode {
    FacialRecAuthFailure(200,false,"900100","人脸核身认证失败"),


    // 占位
    OK(200,true,"000000","成功");

    private final int status;
    private final boolean success;
    private final String code;
    private final String msg;
}
