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

package com.asialjim.microapplet.mams.user.service.login.pc;

import com.asialjim.microapplet.common.security.MamsSession;
import com.asialjim.microapplet.common.utils.PasswordStorage;
import com.asialjim.microapplet.mams.app.cons.ChannelType;
import com.asialjim.microapplet.mams.app.vo.ChlAppVo;
import com.asialjim.microapplet.mams.user.api.UserSessionApi;
import com.asialjim.microapplet.mams.user.service.login.ChlLoginStrategy;
import com.asialjim.microapplet.mams.user.vo.ChlUserVo;
import com.asialjim.microapplet.mams.user.vo.LoginReq;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * PC 登录策略
 *
 * @author <a href="mailto:asialjim@hotmail.com">Asial Jim</a>
 * @version 1.0
 * @since 2025/9/23, &nbsp;&nbsp; <em>version:1.0</em>
 */
@Component
@AllArgsConstructor
public class PCLoginStrategy implements ChlLoginStrategy {
    private final UserSessionApi userSessionApi;

    /**
     * 支持
     *
     * @param type 类型
     * @return boolean
     */
    @Override
    public boolean support(ChannelType type) {
        return ChannelType.PC.equals(type);
    }

    /**
     * 登录
     *
     * @param chlApp 的背影应用
     * @param req    要求的
     * @return {@link MamsSession}
     */
    @Override
    public MamsSession login(ChlAppVo chlApp, LoginReq req) {
        ChlUserVo vo = new ChlUserVo();
        vo.setAppid(chlApp.getAppid());
        vo.setChlType(chlApp.getChlType());
        vo.setChlAppId(chlApp.getChlAppId());
        vo.setChlAppType(chlApp.getChlAppType());
        vo.setChlUserId(req.getUsername());
        vo.setChlUnionId("");
        vo.setChlUserCode(req.getPassword());
        vo.setChlUserToken(req.getCode());
        vo.setCreateIfAbsent(false);
        MamsSession login = userSessionApi.login(vo);
        PasswordStorage.verifyPassword(req.getPassword(), login.getChlUserCode());
        return login;
    }
}