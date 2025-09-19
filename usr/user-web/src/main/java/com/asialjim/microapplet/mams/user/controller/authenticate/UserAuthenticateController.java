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

package com.asialjim.microapplet.mams.user.controller.authenticate;

import com.asialjim.microapplet.commons.security.HasRole;
import com.asialjim.microapplet.commons.security.Tourist;
import com.asialjim.microapplet.mams.user.interfaces.UserAuthenticateInterface;
import com.asialjim.microapplet.mams.user.vo.RegReqVo;
import com.asialjim.microapplet.mams.user.interfaces.endpoint.UserAuthenticateEndpoint;
import com.asialjim.microapplet.mams.user.pojo.UserMain;
import com.asialjim.microapplet.mams.user.vo.UserRegOrLoginReq;
import com.asialjim.microapplet.web.mvc.annotation.ResultWrap;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * 用户认证服务
 *
 * @author <a href="mailto:asialjim@hotmail.com">Asial Jim</a>
 * @version 1.0
 * @since 2025/4/10, &nbsp;&nbsp; <em>version:1.0</em>
 */
@RestController
@AllArgsConstructor
@RequestMapping(UserAuthenticateInterface.PATH)
public class UserAuthenticateController implements UserAuthenticateInterface {
    private final UserAuthenticateEndpoint userAuthenticationService;

    /**
     * 登录注销
     */
    @GetMapping("/logout")
    public void logout(@PathVariable("appid") String appid) {
        this.userAuthenticationService.logout();
    }

    /**
     * 获取当前登录用户
     *
     * @return {@link UserMain }
     * @since 2025/4/10
     */
    @GetMapping("/current")
    public UserMain current(@PathVariable("appid") String appid) {
        return this.userAuthenticationService.current();
    }

    /**
     * 用户注册
     *
     * @return {@link UserMain }
     * @since 2025/4/10
     */
    @HasRole(Tourist.code)
    @PostMapping("/{chlType}/{chlAppId}/{chlAppType}/registration")
    public UserMain registration(@PathVariable("appid") String appid,
                                 @PathVariable("chlType") String chlType,
                                 @PathVariable("chlAppId") String chlAppId,
                                 @PathVariable("chlAppType") String chlAppType,
                                 @RequestBody RegReqVo body) {

        UserRegOrLoginReq req = RegReqVo.build(appid, chlType, chlAppId, chlAppType, body);
        return userAuthenticationService.registration(req);
    }

    /**
     * 用户登录
     *
     * @since 2025/4/10
     */
    @ResultWrap
    @HasRole(Tourist.code)
    @PostMapping("/{chlType}/{chlAppId}/{chlAppType}/login")
    public String login(@PathVariable("appid") String appid,
                        @PathVariable("chlType") String chlType,
                        @PathVariable("chlAppId") String chlAppId,
                        @PathVariable("chlAppType") String chlAppType,
                        @RequestBody RegReqVo body) {

        UserRegOrLoginReq req = RegReqVo.build(appid, chlType, chlAppId, chlAppType, body);
        return this.userAuthenticationService.login(req);
    }
}