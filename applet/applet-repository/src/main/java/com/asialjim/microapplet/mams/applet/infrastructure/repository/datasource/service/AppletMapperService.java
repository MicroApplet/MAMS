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

package com.asialjim.microapplet.mams.applet.infrastructure.repository.datasource.service;

import com.asialjim.microapplet.mams.applet.infrastructure.repository.datasource.po.AppletPo;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.service.IService;

/**
 * 应用信息存储
 *
 * @author <a href="mailto:asialjim@hotmail.com">Asial Jim</a>
 * @version 1.0
 * @since 2025/4/15, &nbsp;&nbsp; <em>version:1.0</em>
 */
public interface AppletMapperService extends IService<AppletPo> {
    AppletPo queryById(String appletId);

    boolean save(AppletPo appletPo);

    Page<AppletPo> pageOf(Page<AppletPo> appletPage, AppletPo po);
}