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

package com.asialjim.microapplet.mams.applet.infrastructure.repository.datasource.po;

import com.asialjim.microapplet.mams.applet.domain.AppletStatus;
import com.asialjim.microapplet.mams.applet.pojo.Applet;
import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import com.mybatisflex.core.keygen.KeyGenerators;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

/**
 * 应用信息ORM实体
 *
 * @author <a href="mailto:asialjim@hotmail.com">Asial Jim</a>
 * @version 1.0
 * @since 2025/4/15, &nbsp;&nbsp; <em>version:1.0</em>
 */
@Data
@Table("applet")
public class AppletPo implements Serializable {
    private static final long serialVersionUID = -6885494239282113784L;

    /**
     * 应用编号
     */
    @Id(keyType = KeyType.Generator, value = KeyGenerators.uuid)
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
    private Boolean deleted;

    /**
     * 应用创建时间
     */
    @Column(onInsertValue = "now()")
    private LocalDateTime createTime;

    /**
     * 应用更新时间
     */
    @Column(onInsertValue = "now()",onUpdateValue = "now()")
    private LocalDateTime updateTime;

    public static AppletPo toPo(Applet body){
        if (Objects.isNull(body))
            return null;
        AppletPo po = new AppletPo();
        po.setId(body.getId());
        po.setName(body.getName());
        po.setOrgId(body.getOrgId());
        po.setStatus(Optional.of(body).map(Applet::getStatus).map(AppletStatus::getCode).orElse(""));
        po.setDeleted(body.getDeleted());
        po.setCreateTime(body.getCreateTime());
        po.setUpdateTime(body.getUpdateTime());
        return po;
    }
    public static Applet fromPo(AppletPo body){
        if (Objects.isNull(body))
            return null;
        Applet po = new Applet();
        po.setId(body.getId());
        po.setName(body.getName());
        po.setOrgId(body.getOrgId());
        po.setStatus(AppletStatus.codeOf(body.getStatus()));
        po.setDeleted(body.getDeleted());
        po.setCreateTime(body.getCreateTime());
        po.setUpdateTime(body.getUpdateTime());
        return po;
    }
}
