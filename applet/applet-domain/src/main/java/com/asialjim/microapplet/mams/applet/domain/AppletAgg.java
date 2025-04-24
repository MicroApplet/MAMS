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

package com.asialjim.microapplet.mams.applet.domain;

import java.util.List;
import java.util.Objects;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.asialjim.microapplet.mams.applet.infrastructure.repository.AppletRepository;
import com.asialjim.microapplet.mams.applet.infrastructure.repository.ChlAppletRepository;
import com.asialjim.microapplet.mams.applet.pojo.Applet;
import com.asialjim.microapplet.mams.applet.pojo.ChlApplet;

import lombok.*;

/**
 * 应用聚合根
 *
 * @author <a href="mailto:asialjim@hotmail.com">Asial Jim</a>
 * @version 1.0
 * @since 2025/4/10, &nbsp;&nbsp; <em>version:1.0</em>
 */
@Getter
@Component
@Scope("request")
public class AppletAgg {
    private final AppletRepository appletRepository;
    private final ChlAppletRepository chlAppletRepository;

    private String appletId;
    /**
     * -- GETTER --
     *  获取当前聚合上下文中的 Applet 实体
     */
    private Applet applet;
    private List<ChlApplet> chlAppletList;

    public AppletAgg(AppletRepository appletRepository,
            ChlAppletRepository chlAppletRepository) {
        this.appletRepository = appletRepository;
        this.chlAppletRepository = chlAppletRepository;
    }

    /**
     * 获取应用已配置的三方渠道应用
     *
     * @return {@link List<ChlApplet>}
     * @since 2025/4/10
     */
    public List<ChlApplet> chlAppletList() {
        if (Objects.nonNull(this.chlAppletList))
            return this.chlAppletList;

        this.chlAppletList = this.chlAppletRepository.listByAppletId(this.appletId);
        return this.chlAppletList;
    }

    /**
     * 初始化应用编号
     *
     * @param id {@link String id}
     * @return {@link AppletAgg }
     * @since 2025/4/10
     */
    public AppletAgg withId(final String id) {
        this.appletId = id;
        if (StringUtils.isNotBlank(this.appletId))
            this.applet = this.appletRepository.queryById(this.appletId);
        return this;
    }

    /**
     * 初始化应用
     *
     * @param applet {@link Applet applet}
     * @return {@link AppletAgg }
     * @since 2025/4/10
     */
    public AppletAgg withId(final Applet applet) {
        if (Objects.isNull(applet))
            return this;

        this.applet = applet;
        this.appletId = this.applet.getId();
        return this;
    }

    /**
     * 根据渠道应用编号查询渠道应用
     *
     * @param chlAppId 渠道应用编号
     * @return {@link ChlApplet}
     * @since 2025/4/15
     */
    public ChlApplet getChlAppletByChlAppId(String chlAppId) {
        return this.chlAppletRepository.queryByChlAppId(chlAppId);
    }
}