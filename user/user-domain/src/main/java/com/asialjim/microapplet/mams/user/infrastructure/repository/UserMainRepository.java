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

package com.asialjim.microapplet.mams.user.infrastructure.repository;

import com.asialjim.microapplet.mams.user.domain.agg.SessionUser;
import com.asialjim.microapplet.mams.user.pojo.UserMain;

/**
 * 主用户信息存储
 *
 * @author <a href="mailto:asialjim@hotmail.com">Asial Jim</a>
 * @version 1.0
 * @since 2025/4/10, &nbsp;&nbsp; <em>version:1.0</em>
 */
public interface UserMainRepository {
    /**
     * 通过用户编号查询主用户信息
     *
     * @param id {@link String id}
     * @return {@link UserMain }
     * @since 2025/4/10
     */
    UserMain queryById(String id);
    /**
     * 通过会话查询主用户信息
	 * @param sessionUser {@link SessionUser sessionUser}
     * @return {@link UserMain }
     * @since 2025/4/10
     */
    UserMain queryBySession(SessionUser sessionUser);

    /**
     * 新增用户,并返回新增的用户编号
	 * @param userMain {@link UserMain userMain}
     * @since 2025/4/10
     */
    String saveAndGetId(UserMain userMain);
}