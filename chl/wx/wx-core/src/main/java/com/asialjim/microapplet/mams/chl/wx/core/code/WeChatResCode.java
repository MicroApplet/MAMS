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

package com.asialjim.microapplet.mams.chl.wx.core.code;

import com.asialjim.microapplet.commons.standard.context.ResCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 微信渠道错误码
 * <p>代码范围：120000-120999</p>
 *
 * @author Asial Jim
 * @version 1.0
 * @since 2026/6/24, &nbsp;&nbsp; <em>version:1.0</em>
 */
@Getter
@AllArgsConstructor
public enum WeChatResCode implements ResCode {
    AppNotFound(500, false, "120001", "微信应用不存在"),
    AppSecretMissing(500, false, "120002", "微信应用密钥缺失"),
    AccessTokenObtainFailure(500, false, "120003", "微信 access_token 获取失败"),
    OK(200, true, "0", "成功");

    private final int status;
    private final boolean success;
    private final String code;
    private final String msg;
}
