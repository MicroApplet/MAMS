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

package com.asialjim.microapplet.mams.chl.wx.core.token;

import com.asialjim.microapplet.mams.chl.wx.core.application.WeChatApplication;
import com.asialjim.microapplet.mams.chl.wx.core.application.WeChatApplicationRepository;
import com.asialjim.microapplet.mams.chl.wx.core.client.WeChatAccessTokenClient;
import com.asialjim.microapplet.mams.chl.wx.core.client.WeChatClientFactory;
import com.asialjim.microapplet.mams.chl.wx.core.code.WeChatResCode;
import com.asialjim.microapplet.mams.chl.wx.core.context.WeChatAccessTokenRes;
import com.asialjim.microapplet.mams.chl.wx.core.context.WeChatApiException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Component;

import java.util.concurrent.locks.Lock;

/**
 * 微信 access_token 仓储实现
 *
 * @author Asial Jim
 * @version 1.0
 * @since 2026/6/24, &nbsp;&nbsp; <em>version:1.0</em>
 */
@Component
public class WeChatAccessTokenRepositoryImpl implements WeChatAccessTokenRepository {
    private static final String GRANT_TYPE_CLIENT_CREDENTIAL = "client_credential";

    private final WeChatApplicationRepository applicationRepository;
    private final WeChatAccessTokenCache accessTokenCache;
    private final ObjectProvider<WeChatClientFactory> clientFactory;

    public WeChatAccessTokenRepositoryImpl(WeChatApplicationRepository applicationRepository,
                                           WeChatAccessTokenCache accessTokenCache,
                                           ObjectProvider<WeChatClientFactory> clientFactory) {
        this.applicationRepository = applicationRepository;
        this.accessTokenCache = accessTokenCache;
        this.clientFactory = clientFactory;
    }

    @Override
    public String accessToken(String appidOrIndex) {
        WeChatApplication app = applicationRepository.resolve(appidOrIndex);
        return accessToken(app);
    }

    @Override
    public String refreshAccessToken(String appidOrIndex) {
        WeChatApplication app = applicationRepository.resolve(appidOrIndex);
        accessTokenCache.remove(app.getAppid());
        return obtainAndCache(app);
    }

    private String accessToken(WeChatApplication app) {
        String appid = app.getAppid();
        String accessToken = accessTokenCache.get(appid);
        if (StringUtils.isNotBlank(accessToken)) return accessToken;

        Lock lock = accessTokenCache.lock(appid);
        lock.lock();
        try {
            accessToken = accessTokenCache.get(appid);
            if (StringUtils.isNotBlank(accessToken)) return accessToken;
            return obtainAndCache(app);
        } finally {
            lock.unlock();
        }
    }

    private String obtainAndCache(WeChatApplication app) {
        if (StringUtils.isBlank(app.getAppid()))
            throw WeChatResCode.AppNotFound.ex();
        if (StringUtils.isBlank(app.getSecret()))
            throw WeChatResCode.AppSecretMissing.ex();

        WeChatAccessTokenClient client = clientFactory.getObject().create(WeChatAccessTokenClient.class);
        WeChatAccessTokenRes res = client.token(true, GRANT_TYPE_CLIENT_CREDENTIAL, app.getAppid(), app.getSecret());
        if (res == null || !res.success() || StringUtils.isBlank(res.getAccessToken())) {
            if (res != null) throw new WeChatApiException(res);
            throw WeChatResCode.AccessTokenObtainFailure.ex();
        }

        accessTokenCache.set(app.getAppid(), res.getAccessToken());
        return res.getAccessToken();
    }
}
