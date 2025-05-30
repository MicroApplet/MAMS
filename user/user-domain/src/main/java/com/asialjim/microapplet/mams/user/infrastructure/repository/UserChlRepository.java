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

import com.asialjim.microapplet.mams.channel.base.ChlAppType;
import com.asialjim.microapplet.mams.channel.base.ChlType;
import com.asialjim.microapplet.mams.user.pojo.UserChl;

import java.util.List;

/**
 * 渠道用户信息存储
 *
 * @author <a href="mailto:asialjim@hotmail.com">Asial Jim</a>
 * @version 1.0
 * @since 2025/4/10, &nbsp;&nbsp; <em>version:1.0</em>
 */
public interface UserChlRepository {

    /**
     * 根据用户名查询渠道用户
     *
     * @param chlType    {@link ChlType 渠道类型}
     * @param chlAppId   {@link String 渠道应用编号}
     * @param chlAppType {@link ChlAppType 渠道应用类型}
     * @param userCode   {@link String 标识}
     * @return {@link UserChl }
     * @since 2025/4/10
     */
    UserChl queryChlUser(ChlType chlType, String chlAppId, ChlAppType chlAppType, String userCode);

    /**
     * 新增渠道用户并返回渠道用户表主键
     *
     * @param target {@link UserChl target}
     * @return {@link String }
     * @since 2025/4/10
     */
    String saveAndGetId(UserChl target);

    /**
     * 根据用户编号和应用编号获取该用户的所有渠道用户信息
     *
     * @param id       {@link String id}
     * @param appletId {@link String appletId}
     * @return {@link List<UserChl> }
     * @since 2025/4/10
     */
    List<UserChl> queryByUseridOfApplet(String id, String appletId);
}