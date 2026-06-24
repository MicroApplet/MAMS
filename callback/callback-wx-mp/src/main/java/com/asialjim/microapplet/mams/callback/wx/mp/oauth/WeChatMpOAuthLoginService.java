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

package com.asialjim.microapplet.mams.callback.wx.mp.oauth;

import com.asialjim.microapplet.mams.chl.wx.core.application.WeChatApplication;
import com.asialjim.microapplet.mams.chl.wx.core.application.WeChatApplicationRepository;
import com.asialjim.microapplet.mams.chl.wx.core.client.WeChatClientFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 微信网页授权登录服务
 *
 * @author Asial Jim
 * @version 1.0
 * @since 2026/6/24, &nbsp;&nbsp; <em>version:1.0</em>
 */
@Service
@RequiredArgsConstructor
public class WeChatMpOAuthLoginService {
    private static final String GRANT_TYPE_AUTHORIZATION_CODE = "authorization_code";

    private final WeChatApplicationRepository applicationRepository;
    private final WeChatClientFactory clientFactory;
    private final OAuthPageLogin oAuthPageLogin;

    public String login(String appid, String code) {
        WeChatApplication app = applicationRepository.resolve(appid);
        WeChatOAuthUserClient client = clientFactory.create(WeChatOAuthUserClient.class);
        WeChatOAuthAccessTokenRes res = client.accessToken(true, app.getAppid(), app.getSecret(), code, GRANT_TYPE_AUTHORIZATION_CODE);
        if (!res.success()) throw new IllegalStateException(res.getErrmsg());
        return oAuthPageLogin.login(appid, code, res);
    }
}
