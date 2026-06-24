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

package com.asialjim.microapplet.user.web;


import com.asialjim.microapplet.session.Session;
import com.asialjim.microapplet.session.SessionCtx;
import com.asialjim.microapplet.user.cloud.PhoneCloud;
import com.asialjim.microapplet.user.entity.web.req.PhoneSmsTokenCheckReq;
import com.asialjim.microapplet.user.entity.web.req.PhoneSmsTokenObtainReq;
import com.asialjim.microapplet.user.entity.web.req.SmsTokenObtainReq;
import com.asialjim.microapplet.user.entity.web.req.SmsTokenPreCheckReq;
import com.asialjim.microapplet.user.entity.web.res.PhoneSmsTokenObtainRes;
import com.asialjim.microapplet.user.entity.web.res.SmsTokenObtainRes;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 手机号相关服务
 *
 * @author Asial Jim
 * @version 1.0
 * @since 2026/3/26, &nbsp;&nbsp; <em>version:1.0</em>
 */
@RestController
@RequestMapping("/phone")
public class PhoneController  {
    @Resource
    private PhoneCloud phoneCloud;
    @Resource
    private SessionCtx authenticator;

    /**
     * 通用短信验证码预校验
     *
     * @since 2026/3/26
     */
    @PostMapping("/sms-token/pre-check")
    public String threeFactorRealNameAuthSmsTokenPreCheck(@RequestBody SmsTokenPreCheckReq body) {
        Session userSession = this.authenticator.currentLoginSession();
        PhoneSmsTokenCheckReq preBody = new PhoneSmsTokenCheckReq();
        preBody.setUserid(userSession.getUserCode());
        preBody.setPhone(body.getPhone());
        preBody.setTokenType(body.getTokenType());
        preBody.setSmsToken(body.getSmsToken());
        preBody.setParams(body.getParams());

        return this.phoneCloud.preCheck(preBody);
    }

    /**
     * 通用短信验证码发送接口
     *
     * @param body 短信验证码获取请求
     * @return 短信验证码获取结果
     * @since 2026/4/8
     */
    @PostMapping("/sms-token/obtain")
    public SmsTokenObtainRes obtainSmsToken(@RequestBody SmsTokenObtainReq body) {
        Session userSession = this.authenticator.currentLoginSession();
        PhoneSmsTokenObtainReq obtainBody = new PhoneSmsTokenObtainReq();
        obtainBody.setUserCode(userSession.getUserCode());
        obtainBody.setPhone(body.getPhone());
        obtainBody.setTokenType(body.getTokenType());
        obtainBody.setParams(body.getParams());

        PhoneSmsTokenObtainRes cloudRes = this.phoneCloud.obtain(obtainBody);

        return SmsTokenObtainRes.builder()
                .id(cloudRes.getId())
                .userid(cloudRes.getUserid())
                .phone(cloudRes.getPhone())
                .tokenType(cloudRes.getTokenType())
                .smsToken(cloudRes.getSmsToken())
                .createTime(cloudRes.getCreateTime())
                .build();
    }
}
