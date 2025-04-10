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

package com.asialjim.microapplet.mams.user.pojo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 主用户信息
 *
 * @author <a href="mailto:asialjim@hotmail.com">Asial Jim</a>
 * @version 1.0
 * @since 2025/4/10, &nbsp;&nbsp; <em>version:1.0</em>
 */
@Data
@Accessors(chain = true)
public class UserMain implements Serializable {
    private static final long serialVersionUID = 652374958068007435L;

    /**
     * 用户编号
     */
    private String id;
    private String appletId;

    /**
     * 用户所属机构号
     *
     * @see com.asialjim.microapplet.mams.applet.pojo.Applet#getOrgId()
     * @see com.asialjim.microapplet.mams.org.pojo.Organization#getId()
     */
    @SuppressWarnings("JavadocReference")
    private String orgId;

    /**
     * 用户昵称
     */
    private String nickname;

    /**
     * 用户逻辑删除标识
     */
    private Boolean deleted;

    /**
     * 首次注册时间
     */
    private LocalDateTime createTime;

    /**
     * 用户更新时间
     */
    private LocalDateTime updateTime;
}