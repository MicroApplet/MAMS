/*
 *  Copyright 2014-2025 <a href="mailto:asialjim@qq.com">Asial Jim</a>
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *   limitations under the License.
 */

package com.asialjim.microapplet.mams.user.res;

import com.asialjim.microapplet.common.context.ResCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 用户系统相关响应代码
 *
 * @author <a href="mailto:asialjim@hotmail.com">Asial Jim</a>
 * @version 1.0
 * @since 2025/2/27, &nbsp;&nbsp; <em>version:1.0</em>
 */
@Getter
@AllArgsConstructor
public enum UserSysResCode implements ResCode {
    UnSupportLoginChannel("USER:LOGIN:CHANNEL:EXCEPTION","登录渠道不可用","不支持的登录渠道"),
    CurrentUserBeanNotInstance("USER:SYS:EXCEPTION","功能升级中，暂不可用","当前登录用户组件未实例化[CurrentUserBeanNotInstance]");

    private final String code;
    private final String msg;
    private final String trace;

    @Override
    public boolean isSuccess() {
        return false;
    }
}