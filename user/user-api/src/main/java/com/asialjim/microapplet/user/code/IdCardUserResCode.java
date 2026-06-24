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
 * 实名证件响应代码
 * 码值范围：113000 -> 113999
 *
 * @author Asial Jim
 * @version 1.0
 * @since 2026/3/17, &nbsp;&nbsp; <em>version:1.0</em>
 */
@Getter
@AllArgsConstructor
public enum IdCardUserResCode implements ResCode {

    OpenidHadLinkedIdCard(200,false,"113001","该用户已经绑定了证件信息"),
    AddIdCardUserFailed(500,false,"113002","添加实名证件信息失败"),
    NameAndIdCardMismatch(200,false,"113003","姓名与证件号不匹配"),

    // 占位
    OK(200, true, "0", "成功");

    private final int status;
    private final boolean success;
    private final String code;
    private final String msg;
}
