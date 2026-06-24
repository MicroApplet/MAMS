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
package com.asialjim.microapplet.user.component.sms;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.io.Serial;
import java.io.Serializable;

@Data
@Configuration
@ConfigurationProperties(value = SMSProperty.PREFIX)
public class SMSProperty implements Serializable {

    @Serial
    private static final long serialVersionUID = -988870899423308513L;
    public static final String PREFIX = "mams.sms";

    /**
     * 是否反显
     */
    private Back back;

    @Data
    public static class Back implements Serializable {

        @Serial
        private static final long serialVersionUID = -5760484487745994383L;
        private boolean show;
    }

    private boolean enable;

    /**
     * 时间限制，当前用户输入错误验证码次数超过此值时，用户获取验证码资格将会被锁定
     */
    private int maxErrorCheckTime = 3;

    /**
     * 一定时间内，用户输入短信验证码错误次数超过此属性值，则锁定该用户获取验证码的资格一段时间
     */
    private int maxErrorCheckNo = 3;

    /**
     * 一定时间内，用户输入短信验证码错误次数超过 maxErrorCheckNo 值， 则该用户验证码获取资格锁定 此值属性的时间，单位：小时
     */
    private int lockHoursWhenSMSError = 1;

    /**
     * 用户、手机号 一分钟内只允许获取一次验证码
     */
    private int gapMinutesBetweenSMS = 1;

    /**
     * 单个用户在 指定时间内 最大允许获取验证码次数
     */
    private int maxUserSMSNo = 20;

    /**
     * 单个用户 在 当前值 时间内，最大允许获取验证码 maxUserSMSNo 次数的 时间限制, 单位：分钟
     */
    private int maxUserSMSTime = 120;

    /**
     * 单个手机号在指定时间内，最大允许获取验证码次数
     */
    private int maxTelSMSNo = 10;

    /**
     * 单个手机号 在当前值 时间内，最大允许获取验证码 maxTelSMSNo 次数的 时间限制, 单位：分钟
     */
    private int maxTelSMSTime = 120;

    /**
     * 单用户业务，在单位时间内， 最大允许发送短信验证码次数
     */
    private int maxBizSMSNo = 3;

    /**
     * 单用户业务，在单位时间内，最大允许发送短信验证码次数的 时间限制， 单位：分钟
     */
    private int maxBizSMSTime = 10;

    /**
     * 短信验证码创建后，可验证的时间, 单位：分钟
     */
    private int smsValidTime = 3;
}