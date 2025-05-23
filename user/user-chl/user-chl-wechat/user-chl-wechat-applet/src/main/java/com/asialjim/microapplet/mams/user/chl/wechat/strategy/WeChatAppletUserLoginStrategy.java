/*
 *  Copyright 2014-2025 <a href="mailto:asialjim@qq.com">Asial Jim</a>
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *   limitations under the License.
 */

package com.asialjim.microapplet.mams.user.chl.wechat.strategy;

import com.asialjim.microapplet.common.application.App;
import com.asialjim.microapplet.mams.channel.base.ChlAppType;
import com.asialjim.microapplet.mams.channel.base.ChlType;
import com.asialjim.microapplet.mams.user.chl.wechat.infrastructure.adapter.WeChatAppletUserAdapter;
import com.asialjim.microapplet.mams.user.chl.wechat.infrastructure.adapter.WeChatChlAppletAdapter;
import com.asialjim.microapplet.mams.user.chl.wechat.po.WeChatAppChl;
import com.asialjim.microapplet.mams.user.command.UserLoginCommand;
import com.asialjim.microapplet.mams.user.command.UserRegCommand;
import com.asialjim.microapplet.mams.user.domain.agg.SessionUser;
import com.asialjim.microapplet.mams.user.pojo.UserChl;
import org.springframework.stereotype.Component;

/**
 * 微信公众号用户登录策略
 *
 * @author <a href="mailto:asialjim@hotmail.com">Asial Jim</a>
 * @version 1.0
 * @since 2025/4/10, &nbsp;&nbsp; <em>version:1.0</em>
 */
@Component
public class WeChatAppletUserLoginStrategy extends WeChatUserLoginStrategy {
    private final WeChatAppletUserAdapter weChatAppletUserAdapter;
    public WeChatAppletUserLoginStrategy(WeChatChlAppletAdapter chlAppletAdapter, WeChatAppletUserAdapter weChatOfficialUserAdapter) {
        super(chlAppletAdapter);
        this.weChatAppletUserAdapter = weChatOfficialUserAdapter;
    }

    @Override
    public ChlAppType chlAppType() {
        return ChlAppType.WeChatApplet;
    }

    @Override
    public SessionUser doLogin(WeChatAppChl weChatAppChl, UserLoginCommand command) {
        String code = command.getReq().getCode();
        UserChl user = this.weChatAppletUserAdapter.login(weChatAppChl, code);

        SessionUser sessionUser = App.beanOrNull(SessionUser.class);
        sessionUser.setUserid(user.getUserid());
        sessionUser.setUsername(user.getChlUserId());
        sessionUser.setChlType(ChlType.WeChat.getCode());
        return sessionUser;
    }

    @Override
    public UserChl doRegistration(WeChatAppChl weChatAppChl, UserRegCommand command) {
        String code = command.getReq().getCode();
        return this.weChatAppletUserAdapter.login(weChatAppChl,code);
    }
}