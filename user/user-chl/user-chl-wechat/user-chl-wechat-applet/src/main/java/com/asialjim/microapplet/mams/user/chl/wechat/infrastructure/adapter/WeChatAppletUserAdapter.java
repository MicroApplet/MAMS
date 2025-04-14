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

package com.asialjim.microapplet.mams.user.chl.wechat.infrastructure.adapter;


import com.asialjim.microapplet.mams.channel.base.NormalChlType;
import com.asialjim.microapplet.mams.user.chl.wechat.po.WeChatAppChl;
import com.asialjim.microapplet.mams.user.chl.wechat.strategy.WeChatChlAppType;
import com.asialjim.microapplet.mams.user.pojo.UserChl;
import com.asialjim.microapplet.wechat.applet.user.WeChatAppletUserRemoting;
import com.asialjim.microapplet.wechat.applet.user.meta.WeChatAppletUserLoginRes;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 微信公众号用户适配器
 *
 * @author <a href="mailto:asialjim@hotmail.com">Asial Jim</a>
 * @version 1.0
 * @since 2025/4/10, &nbsp;&nbsp; <em>version:1.0</em>
 */
@Component
public class WeChatAppletUserAdapter implements WeChatUserAdapter {
    @Resource
    private WeChatAppletUserRemoting weChatAppletUserRemoting;

    public UserChl login(WeChatAppChl weChatAppChl, String code) {
        WeChatAppletUserLoginRes res = this.weChatAppletUserRemoting.login(weChatAppChl.getAppid(), weChatAppChl.getSecret(), code);
        UserChl userChl = new UserChl();
        userChl.setChlUserId(res.getOpenid());
        userChl.setChlUnionId(res.getUnionid());
        userChl.setChlUserCode(code);
        userChl.setChlUserToken(res.getSessionKey());
        userChl.setChlType(NormalChlType.WeChat);
        userChl.setChlAppId(weChatAppChl.getAppid());
        userChl.setChlAppType(WeChatChlAppType.Applet);
        return userChl;
    }
}