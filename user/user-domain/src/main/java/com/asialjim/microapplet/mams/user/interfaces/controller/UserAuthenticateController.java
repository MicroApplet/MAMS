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

package com.asialjim.microapplet.mams.user.interfaces.controller;

import com.asialjim.microapplet.mams.user.application.UserAuthenticationService;
import com.asialjim.microapplet.mams.user.command.UserRegCommand;
import com.asialjim.microapplet.mams.user.vo.UserRegReq;
import com.asialjim.microapplet.mams.user.pojo.UserMain;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * 用户认证服务
 *
 * @author <a href="mailto:asialjim@hotmail.com">Asial Jim</a>
 * @version 1.0
 * @since 2025/4/10, &nbsp;&nbsp; <em>version:1.0</em>
 */
@Component
@AllArgsConstructor
public class UserAuthenticateController {
    private final UserAuthenticationService userAuthenticationService;

    /**
     * 用户注册
     *
	 * @param req {@link UserRegReq req}
     * @return {@link UserMain }
     * @since 2025/4/10
     */
    public UserMain registration(UserRegReq req) {
        UserRegCommand command = UserRegCommand.commandOf(req);
        return userAuthenticationService.registration(command);
    }
}