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

package com.asialjim.microapplet.mams.channel.wechat.official.infrastructure.repository;

import com.asialjim.microapplet.mams.applet.infrastructure.adaptor.AppletCloud;
import com.asialjim.microapplet.mams.applet.pojo.ChlApplet;
import com.asialjim.microapplet.mams.channel.base.ChlAppType;
import com.asialjim.microapplet.mams.channel.wechat.infrastructure.repository.WeChatAppRepository;
import com.asialjim.microapplet.mams.channel.wechat.pojo.WeChatApp;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Objects;

import static com.asialjim.microapplet.mams.channel.wechat.pojo.WeChatApp.populateFromChlAppletToWeChatApp;

/**
 * 微信应用数据仓库
 *
 * @author <a href="mailto:asialjim@hotmail.com">Asial Jim</a>
 * @version 1.0
 * @since 2025 05 01, &nbsp;&nbsp; <em>version:1.0</em>
 */
@Component
public class WeChatAppRepositoryImpl implements WeChatAppRepository {
    @Resource
    private AppletCloud appletCloud;

    @Override
    public WeChatApp queryById(String id) {
        ChlApplet applet = appletCloud.getChlAppletById(id);
        if (Objects.isNull(applet))
            return null;
        WeChatApp app = new WeChatApp();
        populateFromChlAppletToWeChatApp(app, applet);
        return app;
    }

    @Override
    public WeChatApp queryByIndex(String index) {
        ChlApplet applet = appletCloud.getChlAppletByIndex(index);
        if (Objects.isNull(applet))
            return null;
        WeChatApp app = new WeChatApp();
        populateFromChlAppletToWeChatApp(app, applet);
        return app;
    }

    @Override
    public WeChatApp queryByAppidAndType(String appid, ChlAppType appType) {
        ChlApplet applet = appletCloud.getChlAppletByAppidAndType(appid, appType.getCode());
        if (Objects.isNull(applet))
            return null;
        WeChatApp app = new WeChatApp();
        populateFromChlAppletToWeChatApp(app, applet);
        return app;
    }

    @Override
    public WeChatApp queryBySubjectIdAndType(String subjectId, ChlAppType appType) {
        return null;
    }
}