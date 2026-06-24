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

package com.asialjim.microapplet.user.service.login.impl;

import com.asialjim.microapplet.app.entity.web.AppVo;
import com.asialjim.microapplet.commons.chl.PlatformAppType;
import com.asialjim.microapplet.commons.chl.SupportPlatformAppType;
import com.asialjim.microapplet.session.LoginReqVo;
import com.asialjim.microapplet.session.Session;
import com.asialjim.microapplet.user.service.login.PlatformAppLoginHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class WechatAppletLoginHandler implements PlatformAppLoginHandler {

    @Override
    public PlatformAppType supportAppType() {
        return SupportPlatformAppType.WeChatApplet;
    }

    @Override
    public Session login(String appid, AppVo app, LoginReqVo req) {
        log.info("开始处理: {}微信小程序:{} 登录请求 请求参数: {}", app, appid, req);
        return null;
    }
}
