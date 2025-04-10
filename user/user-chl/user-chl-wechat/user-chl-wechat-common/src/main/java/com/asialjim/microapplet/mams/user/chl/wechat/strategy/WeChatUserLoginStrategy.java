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

package com.asialjim.microapplet.mams.user.chl.wechat.strategy;

import com.asialjim.microapplet.mams.channel.base.ChlAppType;
import com.asialjim.microapplet.mams.user.chl.wechat.infrastructure.adapter.WeChatChlAppletAdapter;
import com.asialjim.microapplet.mams.user.chl.wechat.po.WeChatAppChl;
import com.asialjim.microapplet.mams.user.command.UserRegCommand;
import com.asialjim.microapplet.mams.user.vo.UserRegReq;
import com.asialjim.microapplet.mams.user.pojo.UserChl;
import lombok.AllArgsConstructor;

/**
 * 微信渠道登录策略
 *
 * @author <a href="mailto:asialjim@hotmail.com">Asial Jim</a>
 * @version 1.0
 * @since 2025/4/10, &nbsp;&nbsp; <em>version:1.0</em>
 */
@AllArgsConstructor
public abstract class WeChatUserLoginStrategy {
    protected final WeChatChlAppletAdapter chlAppletAdapter;

    /**
     * 登录渠道应用类型
     */
    public abstract WeChatChlAppType chlAppType();


    public final UserChl registration(UserRegCommand command) {
        UserRegReq req = command.getReq();
        String chlAppId = req.getChlAppId();
        ChlAppType chlAppType = req.getChlAppType();
        WeChatAppChl weChatAppChl = this.chlAppletAdapter.queryByAppidAndType(chlAppId,chlAppType);
        return doRegistration(weChatAppChl,command);
    }

    public abstract UserChl doRegistration(WeChatAppChl weChatAppChl,UserRegCommand command);
}