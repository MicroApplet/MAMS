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

package com.asialjim.microapplet.mams.applet.interfaces.endpoint;

import com.asialjim.microapplet.common.page.PageData;
import com.asialjim.microapplet.common.page.PageParameter;
import com.asialjim.microapplet.mams.applet.pojo.ChlApplet;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import com.asialjim.microapplet.mams.applet.application.AppletAppService;
import com.asialjim.microapplet.mams.applet.pojo.Applet;

import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * 小程序应用接口·
 *
 * @author <a href="mailto:asialjim@hotmail.com">Asial Jim</a>
 * @version 1.0
 * @since 2025 04 20, &nbsp;&nbsp; <em>version:1.0</em>
 */
@Component
@AllArgsConstructor
public class AppletEndpoint {
    private final AppletAppService appletAppService;

    /**
     * 保存应用
	 * @param applet {@link Applet applet}
     * @return {@link Applet }
     * @since 2025/4/24
     */
    public Applet save(Applet applet) {
        return this.appletAppService.save(applet);
    }

    /**
     * 分页查询
     *
     * @param condition            {@link Applet condition}
     * @param pageParameter        {@link Supplier pageParameter}
     * @return {@link PageData<Applet> }
     * @since 2025/4/24
     */
    public PageData<Applet> query(Applet condition,
                                  Supplier<PageParameter> pageParameter) {
        return this.appletAppService.query(condition, pageParameter, PageParameter.pageOf());
    }

    /**
     * 根据应用编号查询应用
     *
     * @param id {@link String id}
     * @return {@link Applet }
     * @since 2025/4/24
     */
    public Applet getAppletById(String id) {
        return appletAppService.getAppletById(id);
    }

    /**
     * 根据应用编号，查询该应用名下的渠道应用列表
     *
     * @param appletId {@link String appletId}
     * @return {@link List<ChlApplet> }
     * @since 2025/4/24
     */
    public List<ChlApplet> getChlAppletListByAppletId(String appletId) {
        return appletAppService.getChlAppletListByAppletId(appletId);
    }

    /**
     * 根据渠道应用编号，获取渠道应用信息
     *
     * @param chlAppId {@link String chlAppId}
     * @return {@link ChlApplet }
     * @since 2025/4/24
     */
    public ChlApplet getChlAppletByChlAppId(String chlAppId) {
        return appletAppService.getChlAppletByChlAppId(chlAppId);
    }
}