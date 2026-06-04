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

package com.asialjim.microapplet.mams.app.vo;

import com.asialjim.microapplet.mams.app.valid.AppCreateGroup;
import com.asialjim.microapplet.mams.app.valid.AppUpdateGroup;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 应用视图对象
 *
 * @author <a href="mailto:asialjim@hotmail.com">Asial Jim</a>
 * @version 1.0
 * @since 2025 05 01, &nbsp;&nbsp; <em>version:1.0</em>
 */
@Data
@Accessors(chain = true)
public class AppVo implements Serializable {
    @Serial
    private static final long serialVersionUID = -8243453995107399187L;

    /**
     * 应用编号
     */
    @NotBlank(message = "PARAM:APP-ID:EMPTY|应用编号不能为空", groups = AppUpdateGroup.class)
    private String id;

    /**
     * 应用名称
     */
    @NotBlank(message = "PARAM:APP-NAME:EMPTY|应用名称不能为空", groups = {AppCreateGroup.class, AppUpdateGroup.class})
    private String name;

    /**
     * 所属组织机构编号
     */
    @NotBlank(message = "PARAM:APP-ORG-ID:EMPTY|应用所属机构不能为空", groups = {AppCreateGroup.class, AppUpdateGroup.class})
    private String orgId;

    /**
     * 应用状态
     */
    private String status;

    /**
     * 删除标识
     */
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