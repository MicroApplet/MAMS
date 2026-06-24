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

package com.asialjim.microapplet.user.component.sms.validator;

import com.asialjim.microapplet.user.code.PhoneAuthCode;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.regex.Pattern;

public interface SMSSendValidator {

    boolean supportSend(String biz);

    void doValidSend(String user, String phone, Map<String, String> params);

    default void validSend(String biz, String user, String phone, Map<String, String> params) {
        if (supportSend(biz))
            doValidSend(user, phone, params);
    }


    @Component
    class Default implements SMSSendValidator {
        private static final String CHINA_PHONE_REGEX = "^1[3-9]\\d{9}$";
        private static final Pattern CHINA_PHONE_PATTERN = Pattern.compile(CHINA_PHONE_REGEX);

        @Override
        public boolean supportSend(String biz) {
            return true;
        }

        @Override
        public void doValidSend(String user, String phone, Map<String, String> params) {
            if (!CHINA_PHONE_PATTERN.matcher(phone).matches()) {
                PhoneAuthCode.PhoneIllegal.thr();
            }
        }
    }
}