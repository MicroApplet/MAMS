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

package com.asialjim.microapplet.mams.user.service.login.wechat;

import com.asialjim.microapplet.common.security.MamsSession;
import com.asialjim.microapplet.mams.app.cons.ChannelAppType;
import com.asialjim.microapplet.mams.app.cons.ChannelType;
import com.asialjim.microapplet.mams.app.vo.ChlAppVo;
import com.asialjim.microapplet.mams.user.api.UserSessionApi;
import com.asialjim.microapplet.mams.user.service.login.wechat.cons.WeChatLoginRes;
import com.asialjim.microapplet.mams.user.vo.ChlUserVo;
import com.asialjim.microapplet.mams.user.vo.LoginReq;
import com.asialjim.microapplet.wechat.applet.user.WeChatAppletUserRemoting;
import com.asialjim.microapplet.wechat.applet.user.meta.WeChatAppletUserLoginRes;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Collections;

/**
 * 微信小程序登录策略
 *
 * @author <a href="mailto:asialjim@hotmail.com">Asial Jim</a>
 * @version 1.0
 * @since 2025/9/23, &nbsp;&nbsp; <em>version:1.0</em>
 */
@Slf4j
@Component
public class WeChatAppLoginStrategy implements WeChatTypeLoginStrategy {
    @Resource
    private UserSessionApi userSessionApi;
    @Resource
    private WeChatAppletUserRemoting weChatAppletUserRemoting;

    /**
     * 支持CHL应用类型
     *
     * @param type 类型
     * @return boolean
     */
    @Override
    public boolean supportChlAppType(ChannelAppType type) {
        return ChannelAppType.WeChatApplet.equals(type);
    }

    /**
     * 登录
     *
     * @param chlApp 渠道应用信息
     * @param req    登录参数
     * @return {@link MamsSession}
     */
    @Override
    public MamsSession login(ChlAppVo chlApp, LoginReq req) {
        WeChatAppletUserLoginRes session;
        try {
            session = this.weChatAppletUserRemoting.login(chlApp.getChlAppId(), chlApp.getChlAppSecret(), req.getCode());
        } catch (Throwable e) {
            throw WeChatLoginRes.WeChatUserLoginFailure.ex(Collections.singletonList(e.getMessage()));
        }

        final ChlUserVo chlUser = new ChlUserVo();
        chlUser.setAppid(chlApp.getAppid());
        chlUser.setChlType(ChannelType.WeChat.getCode());
        chlUser.setChlAppType(ChannelAppType.WeChatApplet.getCode());
        chlUser.setChlAppId(chlApp.getChlAppId());

        chlUser.setChlUserId(session.getOpenid());
        chlUser.setChlUnionId(session.getUnionid());
        chlUser.setChlUserCode(req.getCode());
        chlUser.setChlUserToken(session.getSessionKey());
        return this.userSessionApi.login(chlUser);
    }
}