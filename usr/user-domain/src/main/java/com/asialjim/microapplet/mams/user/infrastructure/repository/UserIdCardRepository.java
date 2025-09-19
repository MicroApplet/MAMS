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

import com.asialjim.microapplet.common.human.IdCardType;
import com.asialjim.microapplet.mams.user.pojo.UserIdCard;

/**
 * 用户证件信息存储
 *
 * @author <a href="mailto:asialjim@hotmail.com">Asial Jim</a>
 * @version 1.0
 * @since 2025/4/10, &nbsp;&nbsp; <em>version:1.0</em>
 */
public interface UserIdCardRepository {
    /**
     * 根据用户编号合并并保存用户信息
     *
     * @param user {@link UserIdCard user}
     * @since 2025/4/10
     */
    void mergeAndSave(UserIdCard user);

    /**
     * 根据应用编号和用户编号查询用户证件
     *
     * @param appletId   {@link String 应用编号}
     * @param idNo       {@link String 用户证件号}
     * @param idCardType {@link IdCardType 证件类型}
     * @return {@link UserIdCard }
     * @since 2025/4/10
     */
    UserIdCard queryByIdNoOfTypeForApplet(String idNo, IdCardType idCardType, String appletId);

    /**
     * 根据用户编号、应用编号和证件类型精确查询用户证件信息
     *
     * @param id         {@link String id}
     * @param idCardType {@link IdCardType idCardType}
     * @param appletId   {@link String appletId}
     * @return {@link UserIdCard }
     * @since 2025/4/10
     */
    UserIdCard queryByUseridOfIdTypeForApplet(String id, IdCardType idCardType, String appletId);
}