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

package com.asialjim.microapplet.mams.applet.infrastructure.repository;


import com.asialjim.microapplet.mams.applet.infrastructure.repository.datasource.po.AppletPo;
import com.asialjim.microapplet.mams.applet.infrastructure.repository.datasource.service.AppletMapperService;
import com.asialjim.microapplet.mams.applet.pojo.Applet;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 应用数据存储
 *
 * @author <a href="mailto:asialjim@hotmail.com">Asial Jim</a>
 * @version 1.0
 * @since 2025/4/10, &nbsp;&nbsp; <em>version:1.0</em>
 */
@Component
public class AppletRepositoryImpl implements AppletRepository {
    @Resource
    private AppletMapperService appletMapperService;

    /**
     * 根据应用编号，查询应用
     *
     * @param appletId {@link String appletId}
     * @return {@link Applet }
     * @since 2025/4/10
     */
    public Applet queryById(String appletId) {
        AppletPo po = this.appletMapperService.queryById(appletId);
        return AppletPo.fromPo(po);
    }
}