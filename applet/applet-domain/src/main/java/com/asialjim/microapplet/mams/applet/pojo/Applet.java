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

package com.asialjim.microapplet.mams.applet.pojo;

import com.asialjim.microapplet.mams.applet.domain.AppletStatus;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 微应用
 *
 * @author <a href="mailto:asialjim@hotmail.com">Asial Jim</a>
 * @version 1.0
 * @since 2025/4/10, &nbsp;&nbsp; <em>version:1.0</em>
 */
@Data
public class Applet implements Serializable {

    /**
     * 应用编号
     */
    private String id;

    /**
     * 应用名称
     */
    private String name;

    /**
     * 所属组织机构编号
     */
    private String orgId;

    /**
     * 应用状态
     */
    private AppletStatus status;
    private Boolean deleted;

    /**
     * 应用创建时间
     */
    private LocalDateTime createTime;

    /**
     * 应用更新时间
     */
    private LocalDateTime updateTime;
}