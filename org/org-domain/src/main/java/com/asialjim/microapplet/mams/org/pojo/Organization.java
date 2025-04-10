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

package com.asialjim.microapplet.mams.org.pojo;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 机构贫血实体
 *
 * @author <a href="mailto:asialjim@hotmail.com">Asial Jim</a>
 * @version 1.0
 * @since 2025/4/10, &nbsp;&nbsp; <em>version:1.0</em>
 */
@Data
public class Organization implements Serializable {
    private static final long serialVersionUID = 1170759178956478325L;

    /**
     * 组织机构代码
     */
    private String id;

    /**
     * 机构名称
     */
    private String name;

    /**
     * 机构类型
     */
    private String type;

    /**
     * 用户逻辑删除标识
     */
    private Boolean deleted;

    private LocalDateTime createTime;
    private LocalDateTime updateTime;

}