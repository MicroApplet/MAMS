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

package com.asialjim.microapplet.mams.channel.base;

import com.asialjim.microapplet.common.context.ResCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 渠道类型相关响应代码
 *
 * @author <a href="mailto:asialjim@hotmail.com">Asial Jim</a>
 * @version 1.0
 * @since 2025/4/10, &nbsp;&nbsp; <em>version:</em>
 */
@Getter
@AllArgsConstructor
public enum ChlAppTypeResCode implements ResCode {
    ChlAppTpUnknown("CHL:APP:TP:UNKNOWN","未知渠道应用类型"),
    ChlAppTpUnknownEnum("CHL:APP:TP:UNKNOWN:ENUM","渠道应用类型枚举未知");

    private final String code;
    private final String msg;


    @Override
    public boolean isSuccess() {
        return false;
    }
}