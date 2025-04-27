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

package com.asialjim.microapplet.mams.applet.application;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

import com.asialjim.microapplet.common.page.PageData;
import com.asialjim.microapplet.common.page.PageParameter;
import com.asialjim.microapplet.mams.applet.infrastructure.repository.AppletRepository;
import lombok.AllArgsConstructor;
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
@AllArgsConstructor
public class AppletAppService {
    private final AppletRepository appletRepository;

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
     * 根据编号查询应用的渠道应用列表
     *
     * @param appletId {@link String appletId}
     * @return {@link List<ChlApplet> }
     * @since 2025/4/24
     */
    public List<ChlApplet> getChlAppletListByAppletId(String appletId) {
        return App.beanOpt(AppletAgg.class)
                .map(item -> item.withId(appletId))
                .map(AppletAgg::chlAppletList)
                .orElseGet(ArrayList::new);
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

    /**
     * 保存应用
     *
     * @param applet {@link Applet applet}
     * @return {@link Applet }
     * @since 2025/4/24
     */
    public Applet save(Applet applet) {
        Optional<AppletAgg> appletAggOpt = App.beanOpt(AppletAgg.class);
        return appletAggOpt.map(item -> item.save(applet)).orElse(applet);
    }

    public PageData<Applet> query(Applet condition,
                                         Supplier<PageParameter> pageParameter,
                                         PageParameter defaultPageParameter) {

        PageParameter parameter = Optional.ofNullable(pageParameter).map(Supplier::get).orElse(defaultPageParameter);
        return this.appletRepository.query(parameter,condition);
    }
}