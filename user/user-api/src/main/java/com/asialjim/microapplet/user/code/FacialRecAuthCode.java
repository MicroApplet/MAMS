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

package com.asialjim.microapplet.user.code;

import com.asialjim.microapplet.commons.standard.context.ResCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 人脸认证相关业务响应代码
 * 码值范围：114100 -> 114199
 *
 * @author Asial Jim
 * @version 1.0
 * @since 2026/3/5, &nbsp;&nbsp; <em>version:1.0</em>
 */
@Getter
@AllArgsConstructor
public enum FacialRecAuthCode implements ResCode {

    UnSupportFacialRecAuthPlatformType(200, false, "113000", "该平台暂不支持人脸认证"),
    WaitingImplement(200, false, "113001", "功能开发中，暂不支持"),

    // 占位
    OK(200, true, "0", "成功");

    private final int status;
    private final boolean success;
    private final String code;
    private final String msg;
}