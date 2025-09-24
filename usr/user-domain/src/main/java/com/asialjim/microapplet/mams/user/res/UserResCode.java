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

package com.asialjim.microapplet.mams.user.res;

import com.asialjim.microapplet.common.context.ResCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 用户相关响应代码
 *
 * @author <a href="mailto:asialjim@hotmail.com">Asial Jim</a>
 * @version 1.0
 * @since 2025/4/10, &nbsp;&nbsp; <em>version:1.0</em>
 */
@Getter
@AllArgsConstructor
public enum UserResCode implements ResCode {
    UsernameUnavailable(400,true,"USER:USERNAME:UNAVAILABLE", "用户名不可用"),
    UserNotLogin(400,true,"USER:LOGIN:ERR", "用户未登录"),
    UserNameOrPasswordErr(400,true,"USER:NAME-PASSWORD:ERR", "用户名或者密码错误"),
    UserHasExist(400,true,"USER:ID-NO:EXIST", "该证件已被其他用户账号注册");

    private final int status;
    private final boolean thr;
    private final String code;
    private final String msg;


}