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

package com.asialjim.microapplet.mams.applet.vo;

import com.asialjim.microapplet.mams.applet.pojo.Applet;
import com.asialjim.microapplet.mams.applet.pojo.AppletStatus;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Optional;

/**
 * 应用视图对象
 *
 * @author <a href="mailto:asialjim@hotmail.com">Asial Jim</a>
 * @version 1.0
 * @since 2025 05 01, &nbsp;&nbsp; <em>version:1.0</em>
 */
@Data
public class AppletVo implements Serializable {
    private static final long serialVersionUID = -8243453995107399187L;

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

    public static Applet from(AppletVo vo) {
        Applet applet = new Applet();
        applet.setId(vo.getId());
        applet.setName(vo.getName());
        applet.setOrgId(vo.getOrgId());
        applet.setStatus(AppletStatus.codeOf(vo.getStatus()));
        applet.setDeleted(vo.getDeleted());
        applet.setCreateTime(vo.getCreateTime());
        applet.setUpdateTime(vo.getUpdateTime());
        return applet;
    }

    public static AppletVo to(Applet vo) {
        AppletVo applet = new AppletVo();
        applet.setId(vo.getId());
        applet.setName(vo.getName());
        applet.setOrgId(vo.getOrgId());
        String status = Optional.ofNullable(vo.getStatus()).map(AppletStatus::getCode).orElse("");
        applet.setStatus(status);
        applet.setDeleted(vo.getDeleted());
        applet.setCreateTime(vo.getCreateTime());
        applet.setUpdateTime(vo.getUpdateTime());
        return applet;
    }
}