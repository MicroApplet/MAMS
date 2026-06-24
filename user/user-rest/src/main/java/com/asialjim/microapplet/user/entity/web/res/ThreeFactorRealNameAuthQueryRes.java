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

package com.asialjim.microapplet.user.entity.web.res;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.io.Serial;
import java.io.Serializable;

/**
 * 三要素实名查询结果
 *
 * @author Asial Jim
 * @version 1.0
 * @since 2026/3/5, &nbsp;&nbsp; <em>version:1.0</em>
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ThreeFactorRealNameAuthQueryRes implements Serializable {
    @Serial
    private static final long serialVersionUID = -3642263184603386836L;
    public static final ThreeFactorRealNameAuthQueryRes NONE = new ThreeFactorRealNameAuthQueryRes();

    public static Boolean hadThreeFactorRealNameAuth(ThreeFactorRealNameAuthQueryRes item) {
        return StringUtils.isNoneBlank(item.getName(), item.getIdNumber(), item.getPhone());
    }

    public static Boolean hadTwoFactorRealNameAuth(ThreeFactorRealNameAuthQueryRes item) {
        return StringUtils.isNoneBlank(item.getName(), item.getIdNumber());
    }

    /**
     * 记录编号，对应主键
     */
    private String id;

    /**
     * 用户姓名
     */
    private String name;
    /**
     * 证件类型
     */
    private String idType;
    /**
     * 证件号
     */
    private String idNumber;

    /**
     * 实名手机号
     */
    private String phone;
}