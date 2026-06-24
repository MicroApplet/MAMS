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


import com.asialjim.microapplet.user.entity.web.req.SmsTokenObtainReq;
import com.asialjim.microapplet.user.entity.web.req.ThreeFactorRealNameAuthReq;
import com.asialjim.microapplet.user.entity.web.res.SmsTokenObtainRes;
import com.asialjim.microapplet.user.entity.web.res.ThreeFactorRealNameAuthQueryRes;
import com.asialjim.microapplet.user.service.RealNameAuthService;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 实名认证API
 *
 * @author Asial Jim
 * @version 1.0
 * @since 2026/3/5, &nbsp;&nbsp; <em>version:1.0</em>
 */
@RestController
@AllArgsConstructor
@RequestMapping("/real-name")
public class RealNameAuthController  {
    private final RealNameAuthService realNameAuthService;

    /**
     * 根据会话编号，查询用户的三要素实名信息
     *
     * @param idType {@link String 证件类型编号}
     * @return {@link ThreeFactorRealNameAuthQueryRes }
     * @since 2026/3/5
     */
    @GetMapping("/3-factor/by-session/{idType}")
    public ThreeFactorRealNameAuthQueryRes queryBySession(@RequestParam String idType) {
        return this.realNameAuthService.queryBySession(idType);
    }

    /**
     * 查询当前会话用户的所有证件类型的三要素实名信息
     *
     * @return {@link List<ThreeFactorRealNameAuthQueryRes> }
     * @since 2026/3/26
     */
    @GetMapping("/3-factor/by-session")
    public List<ThreeFactorRealNameAuthQueryRes> queryListBySession() {
        return this.realNameAuthService.queryListBySession();
    }

    /**
     * 进行三要素认证
     *
     * @param body {@link ThreeFactorRealNameAuthReq body}
     * @return {@link ThreeFactorRealNameAuthQueryRes }
     * @since 2026/3/5
     */
    @PostMapping("/3-factor/by-session")
    public ThreeFactorRealNameAuthQueryRes threeFactorRealNameAuth(@Validated @RequestBody ThreeFactorRealNameAuthReq body) {
        return this.realNameAuthService.realNameBy3Factor(body);
    }

    /**
     * 获取三要素实名短信验证码
     *
     * @param body {@link SmsTokenObtainReq body}
     * @return {@link SmsTokenObtainRes }
     * @since {@code }
     */
    @PostMapping("/3-factor/by-session/sms-token/obtain")
    public SmsTokenObtainRes threeFactorRealNameAuthSmsToken(@Validated @RequestBody SmsTokenObtainReq body) {
        return this.realNameAuthService.threeFactorRealNameAuthSmsToken(body);
    }
}