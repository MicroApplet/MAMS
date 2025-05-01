/*
 *  Copyright 2014-2025 <a href="mailto:asialjim@qq.com">Asial Jim</a>
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *   limitations under the License.
 */

package com.asialjim.microapplet.mams.applet.application;

import com.asialjim.microapplet.mams.applet.infrastructure.repository.AppletRepository;
import com.asialjim.microapplet.mams.applet.infrastructure.repository.ChlAppletRepository;
import com.asialjim.microapplet.mams.applet.pojo.Applet;
import com.asialjim.microapplet.mams.applet.pojo.ChlApplet;
import com.asialjim.microapplet.mams.applet.res.AppletRes;
import com.asialjim.microapplet.mams.applet.res.ChlAppletRes;
import com.asialjim.microapplet.mams.channel.base.ChlAppType;
import com.asialjim.microapplet.mams.channel.base.ChlAppTypeResCode;
import com.asialjim.microapplet.mams.channel.base.ChlType;
import com.asialjim.microapplet.mams.channel.base.ChlTypeResCode;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * 渠道应用服务
 *
 * @author <a href="mailto:asialjim@hotmail.com">Asial Jim</a>
 * @version 1.0
 * @since 2025 05 01, &nbsp;&nbsp; <em>version:1.0</em>
 */
@Component
@AllArgsConstructor
public class ChlAppletAppService {
    private final ChlAppletRepository chlAppletRepository;
    private final AppletRepository appletRepository;


    public ChlApplet save(ChlApplet body) {
        String appletId = body.getAppletId();
        Applet applet = appletRepository.queryById(appletId);
        if (Objects.isNull(applet))
            AppletRes.NoSuchApplet.throwBiz();
        ChlType chlType = body.getChlType();
        if (Objects.isNull(chlType))
            ChlTypeResCode.ChlTypeNotProvide.throwBiz();
        ChlAppType chlAppType = body.getChlAppType();
        if (Objects.isNull(chlAppType))
            ChlAppTypeResCode.ChlAppTpNotProvide.throwBiz();
        String chlAppId = body.getChlAppId();
        if (StringUtils.isBlank(chlAppId))
            ChlAppletRes.ChlAppIdNotSupport.throwBiz();

        return chlAppletRepository.save(body);
    }
}