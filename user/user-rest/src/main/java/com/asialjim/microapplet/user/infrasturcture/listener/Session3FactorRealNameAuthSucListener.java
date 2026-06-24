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

package com.asialjim.microapplet.user.infrasturcture.listener;

import com.asialjim.microapplet.session.Session;
import com.asialjim.microapplet.session.SessionCtx;
import com.asialjim.microapplet.user.event.Session3FactorRealNameAuthSucEvent;
import com.asialjim.microapplet.user.service.UserSessionService;
import jakarta.annotation.Resource;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * 会话三要素实名认证成功监听器
 *
 * @author Asial Jim
 * @version 1.0
 * @since 2026/3/5, &nbsp;&nbsp; <em>version:1.0</em>
 */
@Component
public class Session3FactorRealNameAuthSucListener
        implements ApplicationListener<Session3FactorRealNameAuthSucEvent> {

    @Resource
    private SessionCtx sessionCtx;
    @Resource
    private UserSessionService microBankUserSessionService;

    @Override
    public void onApplicationEvent(Session3FactorRealNameAuthSucEvent event) {
        Session session = event.session();
        Session current = this.sessionCtx.currentSession();

        this.microBankUserSessionService.updateSessionNameIdCardBriefUamIdAndCustomerId(session, current);
    }
}