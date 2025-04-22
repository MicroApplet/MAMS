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

import java.util.Optional;

import org.springframework.stereotype.Component;
import com.asialjim.microapplet.common.application.App;
import com.asialjim.microapplet.mams.applet.domain.AppletAgg;
import com.asialjim.microapplet.mams.applet.pojo.Applet;
import com.asialjim.microapplet.mams.applet.pojo.ChlApplet;

/**
 * 小微应用应用服务
 *
 * @author <a href="mailto:asialjim@hotmail.com">Asial Jim</a>
 * @version 1.0
 * @since 2025 04 20, &nbsp;&nbsp; <em>version:1.0</em>
 */
@Component
public class AppletAppService {

    /**
     * 根据编号查询应用
     *
     * @param id {@link String id}
     * @return {@link Applet }
     * @since 2025/4/22
     */
    public Applet getAppletById(String id) {
        Optional<AppletAgg> appletAggOpt = App.beanOpt(AppletAgg.class);
        return appletAggOpt.map(item -> item.withId(id)).map(AppletAgg::getApplet).orElse(null);
    }

    /**
     * 根据渠道应用编号查询渠道应用
     *
     * @param chlAppId 渠道应用编号
     * @return ChlApplet
     */
    public ChlApplet getChlAppletByChlAppId(String chlAppId) {
        Optional<AppletAgg> appletAggOpt = App.beanOpt(AppletAgg.class);
        return appletAggOpt.map(agg -> agg.getChlAppletByChlAppId(chlAppId)).orElse(null);
    }
}