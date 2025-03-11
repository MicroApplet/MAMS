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

package io.github.microapplet.mams.user.controller;

import io.github.microapplet.commons.security.HasRole;
import io.github.microapplet.commons.security.Tourist;
import io.github.microapplet.mams.user.login.LoginService;
import io.github.microapplet.mams.user.parameter.LoginParameter;
import io.github.microapplet.mams.user.session.SessionUser;
import io.github.microapplet.mams.user.vo.LoginParameterVo;
import io.github.microapplet.mams.user.vo.factory.LoginParameterFactory;
import io.github.microapplet.web.mvc.annotation.ResultWrap;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * 用户认证服务
 *
 * @author <a href="mailto:asialjim@hotmail.com">Asial Jim</a>
 * @version 1.0
 * @since 2025/3/11, &nbsp;&nbsp; <em>version:1.0</em>
 */
@Slf4j
@RestController
@RequestMapping
@AllArgsConstructor
public class UserAuthenticateController {
    private final LoginService loginService;

    @ResultWrap
    @HasRole(Tourist.code)
    @Operation(summary = "登录")
    @PostMapping("/login/{appid}/{chlCode}/{chlAppid}/{chlAppType}")
    public String login(@PathVariable String appid,
                        @PathVariable String chlCode,
                        @PathVariable String chlAppid,
                        @PathVariable String chlAppType,
                        @Parameter @RequestBody LoginParameterVo body) {

        log.info("应用：{},用户在渠道：{} 登录，渠道信息：{}|{},登录参数：{}", appid, chlCode, chlAppid, chlAppType, body);

        LoginParameter parameter = LoginParameterFactory.build(appid, chlCode, chlAppid, chlAppType, body);

        SessionUser login = this.loginService.login(parameter);
        return login.getAuthorization();
    }
}