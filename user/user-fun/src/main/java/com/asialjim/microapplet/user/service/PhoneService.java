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

package com.asialjim.microapplet.user.service;

import com.asialjim.microapplet.commons.chl.PlatformAppType;
import com.asialjim.microapplet.user.code.PhoneAuthCode;
import com.asialjim.microapplet.user.component.sms.SMSOperator;
import com.asialjim.microapplet.user.component.sms.SMSProperty;
import com.asialjim.microapplet.user.cons.SmsTokenType;
import com.asialjim.microapplet.user.entity.vo.ChlUserVo;
import com.asialjim.microapplet.user.entity.web.req.PhoneSmsTokenCheckReq;
import com.asialjim.microapplet.user.entity.web.req.PhoneSmsTokenObtainReq;
import com.asialjim.microapplet.user.entity.web.res.PhoneSmsTokenObtainRes;
import com.asialjim.microapplet.user.infrastructure.adaptor.PhoneAdaptor;
import com.asialjim.microapplet.user.infrastructure.repository.ChlUserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@AllArgsConstructor
public class PhoneService {
    private static final SecureRandom RANDOM = new SecureRandom();
    private static final int SMS_TOKEN_LENGTH = 6;

    private final PhoneAdaptor phoneAdaptor;
    private final ChlUserRepository chlUserRepository;
    private final SMSOperator microBankSMSOperator;
    private final SMSProperty smsProperty;

    public PhoneSmsTokenObtainRes obtain(PhoneSmsTokenObtainReq body) {
        String user = body.getUserCode();
        String biz = body.getTokenType();
        String phone = body.getPhone();
        String smsToken = generate();
        Map<String, String> params = body.getParams();

        this.microBankSMSOperator.create(user, biz, phone, smsToken, params);


        ChlUserVo chlUser = chlUserRepository.queryById(user);
        String appType = chlUser.getAppType();
        String platformType = chlUser.getPlatformType();
        PlatformAppType platformAppType = PlatformAppType.codeOf(platformType, appType);
        SmsTokenType smsTokenType = SmsTokenType.typeOf(body.getTokenType());
        String templateNo = smsTokenType.getTemplateNo();
        String msgContent = smsToken + "|" + smsTokenType.getMsgTemplate();

        this.phoneAdaptor.send(smsToken, phone, platformType, templateNo, msgContent);

        PhoneSmsTokenObtainRes res = new PhoneSmsTokenObtainRes();
        res.setId(UUID.randomUUID().toString());
        res.setUserid(body.getUserCode());
        res.setPhone(body.getPhone());
        res.setTokenType(body.getTokenType());
        boolean backShow = Optional.ofNullable(this.smsProperty).map(SMSProperty::getBack).map(SMSProperty.Back::isShow).orElse(false);
        if (backShow)
            res.setSmsToken(smsToken);
        res.setCreateTime(LocalDateTime.now());
        return res;
    }

    public String check(PhoneSmsTokenCheckReq body) {
        return this.microBankSMSOperator.validate(body.getUserid(), body.getTokenType(), body.getPhone(), body.getSmsToken(), body.getParams());
    }

    public String preCheck(PhoneSmsTokenCheckReq body) {
        String s = this.microBankSMSOperator.preCheck(body.getUserid(), body.getTokenType(), body.getPhone(), body.getSmsToken());
        if (StringUtils.isBlank(s))
            throw PhoneAuthCode.SmsTokenErr.ex();
        return s;
    }

    private static String generate() {
        StringBuilder sb = new StringBuilder(SMS_TOKEN_LENGTH);
        for (int i = 0; i < SMS_TOKEN_LENGTH; i++) {
            sb.append(RANDOM.nextInt(10));
        }
        return sb.toString();
    }

}