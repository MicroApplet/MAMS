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

package com.asialjim.microapplet.mams.callback.web.wx;

import com.asialjim.microapplet.app.cloud.ApplicationCloud;
import com.asialjim.microapplet.app.code.AppCode;
import com.asialjim.microapplet.app.entity.web.AppVo;
import com.asialjim.microapplet.mams.chl.wx.core.application.WeChatApplication;
import com.asialjim.microapplet.mams.chl.wx.core.application.WeChatApplicationRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

/**
 * 基于应用中心的微信应用仓储
 *
 * @author Asial Jim
 * @version 1.0
 * @since 2026/6/24, &nbsp;&nbsp; <em>version:1.0</em>
 */
@Component
@RequiredArgsConstructor
@ConditionalOnMissingBean(WeChatApplicationRepository.class)
public class AppCloudWeChatApplicationRepository implements WeChatApplicationRepository {
    private static final String WECHAT = "wechat";
    private static final String WECHAT_SHORT = "wx";
    private static final String OFFICIAL = "official";
    private static final String MP = "mp";

    private final ApplicationCloud applicationCloud;

    @Override
    public WeChatApplication resolve(String appidOrIndex) {
        if (StringUtils.isBlank(appidOrIndex))
            throw AppCode.NoSuchAppidErr.ex();

        List<AppVo> apps = applicationCloud.queryByAppid(appidOrIndex);
        AppVo app = select(apps, appidOrIndex);
        return convert(app);
    }

    private AppVo select(List<AppVo> apps, String appidOrIndex) {
        if (CollectionUtils.isEmpty(apps))
            throw AppCode.NoSuchAppidErr.exWithData(appidOrIndex);

        List<AppVo> wxApps = apps.stream()
                .filter(this::wechat)
                .toList();

        if (wxApps.size() == 1) return wxApps.getFirst();

        List<AppVo> mpApps = wxApps.stream()
                .filter(this::official)
                .toList();

        if (mpApps.size() == 1) return mpApps.getFirst();
        if (apps.size() == 1) return apps.getFirst();

        throw AppCode.SameAppidErr.exWithData(appidOrIndex);
    }

    private boolean wechat(AppVo app) {
        String platformType = app.getPlatformType();
        return StringUtils.equalsIgnoreCase(platformType, WECHAT)
                || StringUtils.equalsIgnoreCase(platformType, WECHAT_SHORT);
    }

    private boolean official(AppVo app) {
        String appType = app.getAppType();
        return StringUtils.equalsIgnoreCase(appType, OFFICIAL)
                || StringUtils.equalsIgnoreCase(appType, MP);
    }

    private WeChatApplication convert(AppVo app) {
        if (Objects.isNull(app))
            throw AppCode.NoSuchAppidErr.ex();

        return new WeChatApplication()
                .setId(app.getId())
                .setSubjectId(app.getSubjectId())
                .setName(app.getName())
                .setAppid(app.getAppId())
                .setSecret(app.getSecret())
                .setToken(app.getToken())
                .setAesKey(app.getEncodeKeyValue())
                .setEncType(app.getEncodeType());
    }
}
