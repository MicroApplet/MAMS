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


import com.asialjim.microapplet.common.page.PageData;
import com.asialjim.microapplet.common.page.PageParameter;
import com.asialjim.microapplet.mams.applet.pojo.Applet;

/**
 * 应用数据存储
 *
 * @author <a href="mailto:asialjim@hotmail.com">Asial Jim</a>
 * @version 1.0
 * @since 2025/4/10, &nbsp;&nbsp; <em>version:1.0</em>
 */
public interface AppletRepository {

    /**
     * 根据应用编号，查询应用
     *
     * @param appletId {@link String appletId}
     * @return {@link Applet }
     * @since 2025/4/10
     */
    Applet queryById(String appletId);

    /**
     * 保存应用
	 * @param applet {@link Applet applet}
     * @return {@link Applet }
     * @since 2025/4/24
     */
    Applet save(Applet applet);

    PageData<Applet> query( PageParameter page, Applet condition);
}