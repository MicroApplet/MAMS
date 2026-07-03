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

package com.asialjim.microapplet.wx.applet.infrastructure.repository;

import com.asialjim.microapplet.app.cloud.ApplicationCloud;
import com.asialjim.microapplet.app.entity.web.AppVo;
import com.asialjim.microapplet.commons.chl.SupportPlatformType;
import com.asialjim.microapplet.wx.core.application.WeChatApplication;
import com.asialjim.microapplet.wx.core.application.WeChatApplicationRepository;
import com.asialjim.microapplet.wx.core.code.WeChatResCode;
import jakarta.annotation.Resource;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Objects;

@AllArgsConstructor
public class WeChatApplicationRepositoryWithAppAdaptor implements WeChatApplicationRepository {
    private final ApplicationCloud applicationCloud;

    @Override
    public WeChatApplication resolve(String appidOrIndex) {
        AppVo appVo = this.applicationCloud.queryByPlatformTypeAndAppid(SupportPlatformType.WeChat.getCode(), appidOrIndex);
        if (Objects.isNull(appVo))
            WeChatResCode.AppNotFound.thr(appidOrIndex);
        WeChatApplication app = new WeChatApplication();
        app.setId(appVo.getId());
        app.setSubjectId(appVo.getSubjectId());
        app.setName(appVo.getName());
        app.setAppid(appVo.getAppId());
        app.setSecret(appVo.getSecret());
        app.setToken(appVo.getToken());
        app.setAesKey(appVo.getEncodeKeyValue());
        app.setEncType(appVo.getEncodeType());
        return app;
    }
}
