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

package com.asialjim.microapplet.mams.channel.wechat.applet.domain;

import com.asialjim.microapplet.common.application.App;
import com.asialjim.microapplet.mams.channel.base.ChlAppType;
import com.asialjim.microapplet.mams.channel.base.ChlType;
import com.asialjim.microapplet.mams.channel.wechat.applet.cmd.WeChatAppletUserLoginCmd;
import com.asialjim.microapplet.mams.channel.wechat.applet.infrastructure.remoting.user.WeChatAppletUserRemoting;
import com.asialjim.microapplet.mams.channel.wechat.domain.WeChatAppAgg;
import com.asialjim.microapplet.mams.channel.wechat.infrastructure.adaptor.WeChatApiResultEnumeration;
import com.asialjim.microapplet.mams.channel.wechat.pojo.WeChatApp;
import com.asialjim.microapplet.mams.user.infrastructure.adaptor.UserAuthenticateCloud;
import com.asialjim.microapplet.mams.user.vo.RegReqVo;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Objects;
import java.util.Optional;

/**
 * 微信小程序应用聚合根
 *
 * @author <a href="mailto:asialjim@hotmail.com">Asial Jim</a>
 * @version 1.0
 * @since 2025/5/23, &nbsp;&nbsp; <em>version:1.0</em>
 */
@Slf4j
@Setter
@Component
@Scope("request")
public class WeChatAppletAppAgg {
    private WeChatApp weChatApp;
    private WeChatAppAgg weChatAppAgg;

    @Resource
    private UserAuthenticateCloud userAuthenticateCloud;

    public WeChatAppletAppAgg withAppid(String appid) {
        Optional<WeChatAppAgg> opt = App.beanOpt(WeChatAppAgg.class);
        opt.ifPresent(item -> setWeChatAppAgg(item.withId(appid)));
        return this;
    }


    /**
     * 获取对应的公众号应用信息
     *
     * @return {@link WeChatApp}
     * @since 2025/4/29
     */
    public WeChatApp weChatApp() {
        if (Objects.nonNull(weChatApp))
            return weChatApp;

        weChatApp = this.weChatAppAgg.weChatApp();
        if (Objects.isNull(weChatApp))
            throw WeChatApiResultEnumeration.CODE_40013.sysException();
        ChlAppType appType = weChatApp.getAppType();

        if (!ChlAppType.WeChatApplet.equals(appType))
            WeChatApiResultEnumeration.CODE_40013.throwBiz();

        return weChatApp;
    }


    public String login(WeChatAppletUserLoginCmd cmd) {
        WeChatApp app = weChatApp();
        RegReqVo vo = new RegReqVo();
        vo.setCode(cmd.getCode());
        return this.userAuthenticateCloud.login(app.getAppletId(), ChlType.WeChat.getCode(), app.getAppid(), ChlAppType.WeChatApplet.getCode(), vo);
    }
}