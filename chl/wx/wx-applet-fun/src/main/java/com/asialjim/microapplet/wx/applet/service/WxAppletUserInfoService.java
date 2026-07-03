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

package com.asialjim.microapplet.wx.applet.service;

import com.asialjim.microapplet.wx.applet.client.WeChatAppletUserClient;
import com.asialjim.microapplet.wx.applet.client.meta.WeChatAppletUserLoginRes;
import com.asialjim.microapplet.wx.applet.session.WxAppletUserSession;
import com.asialjim.microapplet.wx.core.application.WeChatApplication;
import com.asialjim.microapplet.wx.core.application.WeChatApplicationRepository;
import com.asialjim.microapplet.wx.core.client.WeChatClientFactory;
import com.asialjim.microapplet.wx.core.code.WeChatResCode;
import com.asialjim.microapplet.wx.core.context.WeChatApiRes;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Slf4j
@Service
public class WxAppletUserInfoService {
    @Resource
    private WeChatApplicationRepository weChatApplicationRepository;
    @Resource
    private ObjectProvider<WeChatClientFactory> clientFactory;

    public WxAppletUserSession login(String appid, String code) {
        WeChatApplication app = this.weChatApplicationRepository.resolve(appid);
        if (Objects.isNull(app))
            WeChatResCode.AppNotFound.thr(appid);
        WeChatAppletUserClient client = clientFactory.getObject().create(WeChatAppletUserClient.class);
        WeChatAppletUserLoginRes login = client.login(app.getAppid(), app.getSecret(), code);
        log.info("微信小程序登陆结果：{}",login);
        WeChatApiRes.check(login,"微信小程序登陆");
        if (Objects.isNull(login))
            WeChatResCode.LoginFailure.thr("未正确受到网络响应");
        WxAppletUserSession session = new WxAppletUserSession();
        session.setOpenid(login.getOpenid());
        session.setUnionid(login.getUnionid());
        session.setSessionKey(login.getSessionKey());
        return session;
    }
}