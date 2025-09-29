/*
 *    Copyright 2014-2025 <a href="mailto:asialjim@qq.com">Asial Jim</a>
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package com.asialjim.microapplet.mams.user.web;

import com.asialjim.microapplet.common.context.Res;
import com.asialjim.microapplet.common.context.Result;
import com.asialjim.microapplet.common.security.MamsSession;
import com.asialjim.microapplet.mams.user.api.AuthApi;
import com.asialjim.microapplet.mams.user.service.AuthService;
import com.asialjim.microapplet.mams.user.vo.LoginReq;
import com.asialjim.microapplet.web.mvc.annotation.RwIgnore;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 身份验证控制器
 *
 * @author <a href="mailto:asialjim@hotmail.com">Asial Jim</a>
 * @version 1.0
 * @since 2025/9/22, &nbsp;&nbsp; <em>version:1.0</em>
 */
@RestController
@AllArgsConstructor
@RequestMapping(AuthApi.path)
public class AuthController implements AuthApi {
    private final AuthService authService;

    /**
     * 登录
     *
     * @param appid    appid
     * @param chl      登录渠道
     * @param chlAppid 登录渠道应用编号
     * @param req      登录参数
     * @return {@link String}
     */
    @RwIgnore
    @Override
    public ResponseEntity<Result<String>> login(String appid, String chl, String chlAppid, String chlAppType, LoginReq req) {
        return this.authService.login(appid, chl, chlAppid, chlAppType, req);
    }

    /**
     * 身份验证
     *
     * @param token    令牌
     * @return {@link MamsSession}
     */
    @Override
    public MamsSession auth(String token) {
        return this.authService.auth(token);
    }

    /**
     * 注销
     *
     * @param token 令牌
     */
    @Override
    public String logout(String token) {
        this.authService.logout(token);
        return Res.OK.getMsg();
    }
}