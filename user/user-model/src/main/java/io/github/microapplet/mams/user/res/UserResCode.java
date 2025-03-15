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

package io.github.microapplet.mams.user.res;

import io.github.microapplet.common.context.ResCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 用户相关响应代码
 *
 * @author <a href="mailto:asialjim@hotmail.com">Asial Jim</a>
 * @version 1.0
 * @since 2025/2/27, &nbsp;&nbsp; <em>version:1.0</em>
 */
@Getter
@AllArgsConstructor
public enum UserResCode implements ResCode {

    UnSupportLoginChl(false,"100005","暂不支持该渠道登录"),
    NullLoginParameterErr(false,"100004","登录参数不能为空"),
    UserNameOrPasswordErr(false, "100003", "用户名或者密码错误"),
    UserNotLogin(false, "100002", "用户未登录"){
        @Override
        public int getStatus() {
            return 401;
        }
    },
    UserNameTaken(false, "100001", "用户名被占用");

    private final boolean success;
    private final String code;
    private final String msg;
}