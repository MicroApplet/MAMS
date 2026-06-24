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
 * 手机号认证相关业务响应代码
 * 码值范围：114000 -> 114099
 *
 * @author Asial Jim
 * @version 1.0
 * @since 2026/3/5, &nbsp;&nbsp; <em>version:1.0</em>
 */
@Getter
@AllArgsConstructor
public enum PhoneAuthCode implements ResCode {

    PhoneIllegal(200, false, "114011", "非法手机号"),
    SmsCheckTimeErr(200,false,"114012","验证失败次数超过 %d 次，请 %d 小时后再试"),
    RetryAfterAWhile(200,false,"114013","请在 %d 秒后重试"),
    RetryTimesLimited(200,false,"114014","当前用户%d分钟内获取验证码次数超过%d次，请%d分钟以后再试"),
    RetryTimesLimitedOnBiz(200,false,"114015","当前用户业务%d分钟内获取验证码次数超过%d次，请%d分钟后再试"),
    RetryTimesLimitedOnPhone(200,false,"114015","当前手机%d分钟内获取验证码次数超过%d次，请%d分钟后再试"),
    SmsTokenHadExpired(200, false, "114021", "验证码已过期"),

    SmsTokenErr(200,false,"114022","验证码错误"),
    SmsTokenAndPhoneMismatch(200,false,"114023","验证码与手机号不匹配"),

    // 占位
    OK(200, true, "0", "成功");

    private final int status;
    private final boolean success;
    private final String code;
    private final String msg;
}