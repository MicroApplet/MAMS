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

package com.asialjim.microapplet.mams.applet.infrastructure.repository;

import com.asialjim.microapplet.mams.applet.pojo.ChlApplet;

import java.util.List;

/**
 * 渠道应用数据存储
 *
 * @author <a href="mailto:asialjim@hotmail.com">Asial Jim</a>
 * @version 1.0
 * @since 2025/4/10, &nbsp;&nbsp; <em>version:1.0</em>
 */
public interface ChlAppletRepository {

    /**
     * 获取指定应用编号的渠道应用列表信息,即： queryBy {@link ChlApplet#getAppId()}
     *
     * @param appletId {@link String appletId}
     * @return {@link List<ChlApplet> }
     * @since 2025/4/10
     */
    List<ChlApplet> listByAppletId(String appletId);

    /**
     * 根据渠道应用编号查询渠道应用
     *
     * @param chlAppId 渠道应用编号
     * @return {@link ChlApplet}
     * @since 2025/4/15
     */
    ChlApplet queryByChlAppId(String chlAppId);

    ChlApplet save(ChlApplet body);

    ChlApplet queryById(String id);

    ChlApplet queryByIndex(String index);

    ChlApplet queryByAppidAndType(String appid, String appTypeCode);
}