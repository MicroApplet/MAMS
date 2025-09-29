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

import com.asialjim.microapplet.common.context.Result;
import com.asialjim.microapplet.common.security.MamsSession;
import com.asialjim.microapplet.mams.user.api.UserRegistrarApi;
import com.asialjim.microapplet.mams.user.service.registrar.UserRegistrarService;
import com.asialjim.microapplet.mams.user.vo.ChlUserVo;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户会话控制器
 *
 * @author <a href="mailto:asialjim@hotmail.com">Asial Jim</a>
 * @version 1.0
 * @since 2025/9/19, &nbsp;&nbsp; <em>version:1.0</em>
 */
@RestController
@RequiredArgsConstructor
@RequestMapping(UserRegistrarApi.PATH)
public class UserRegistrarController implements UserRegistrarApi {
    private final UserRegistrarService userRegistrarService;

    /**
     * 注册
     *
     * @param user 用户
     * @return {@link ChlUserVo}
     */
    @Override
    public ChlUserVo register(ChlUserVo user) {
        return this.userRegistrarService.register(user);
    }

    @Override
    public Result<Void> root() {
        return this.userRegistrarService.rootExist();
    }

    /**
     * 下线
     *
     * @param session 会话
     * @return {@link ChlUserVo}
     */
    @Override
    public ChlUserVo logoff(MamsSession session) {
        return null;
    }
}