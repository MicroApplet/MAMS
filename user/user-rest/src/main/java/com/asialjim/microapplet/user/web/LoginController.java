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


import com.asialjim.microapplet.app.code.AgreementType;
import com.asialjim.microapplet.session.LoginReqVo;
import com.asialjim.microapplet.user.agreement.annotation.Agreement;
import com.asialjim.microapplet.user.service.LoginService;
import com.asialjim.microapplet.web.client.MamsHttpHeaders;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

/**
 * 用户登录
 *
 * @author Asial Jim
 * @version 1.0
 * @since 2026/3/5, &nbsp;&nbsp; <em>version:1.0</em>
 */
@RestController
public class LoginController  {
    @Resource
    private LoginService loginService;

    /**
     * 通用登录接口
     *
     * @param host           {@link String 域名}
     * @param appid          {@link String 应用编号}
     * @param requestChannel {@link String 登录平台渠道}
     * @param req            {@link LoginReqVo req}
     * @return {@link String 令牌}
     * @since 2026/3/31
     */
    @PostMapping(
            value = "/login",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @Agreement(after = {
            AgreementType.AppletService,
            AgreementType.PrivacyPolicyAgreement
    })
    public String login(
            @RequestHeader(value = HttpHeaders.HOST, required = false) String host,
            @RequestHeader(MamsHttpHeaders.APP_ID) String appid,
            @RequestHeader(value = MamsHttpHeaders.PLATFORM_TYPE, required = false) String requestChannel,
            @RequestBody LoginReqVo req) {

        return this.loginService.login(host, requestChannel, appid, req);
    }


    @Profile({"dev"})
    @Agreement(after = {
            AgreementType.AppletService,
            AgreementType.PrivacyPolicyAgreement
    })
    @GetMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
    public String loginDev(@RequestParam String chlType, @RequestParam String code) {
        return this.loginService.login(chlType, code);
    }
}