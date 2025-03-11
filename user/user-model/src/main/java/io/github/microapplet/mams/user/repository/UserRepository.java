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

package io.github.microapplet.mams.user.repository;

import io.github.microapplet.mams.user.model.User;

/**
 * 用户数据仓库
 *
 * @author <a href="mailto:asialjim@hotmail.com">Asial Jim</a>
 * @version 1.0
 * @since 2025/3/10, &nbsp;&nbsp; <em>version:1.0</em>
 */
public interface UserRepository {

    /**
     * 用用户编号获取用户信息
     *
     * @param userid {@link String userid}
     * @return {@link User }
     * @since 2025/3/10
     */
    User queryByUserid(String userid);

    /**
     * 用户名、密码查询用户信息
	 * @param username {@link String username}
	 * @param password {@link String password}
     * @return {@link User }
     * @since 2025/3/11
     */
    User queryByAppidUsernameAndPassword(String appid,String username, String password);
}