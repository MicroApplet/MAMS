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

package com.asialjim.microapplet.mams.user.infrastructure.repository.datasource.po;

import com.asialjim.microapplet.mams.user.pojo.UserMain;
import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import com.mybatisflex.core.keygen.KeyGenerators;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * 主用户信息
 *
 * @author <a href="mailto:asialjim@hotmail.com">Asial Jim</a>
 * @version 1.0
 * @since 2025/4/10, &nbsp;&nbsp; <em>version:1.0</em>
 */
@Data
@Table("user_main")
@Accessors(chain = true)
public class UserMainPo implements Serializable {
    private static final long serialVersionUID = 652374958068007435L;

    /**
     * 用户编号
     */
    @Id(keyType = KeyType.Generator, value = KeyGenerators.uuid)
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
    @Column(onInsertValue = "now()")
    private LocalDateTime createTime;

    /**
     * 用户更新时间
     */
    @Column(onInsertValue = "now()",onUpdateValue = "now()")
    private LocalDateTime updateTime;

    public static UserMainPo toPo(UserMain body) {
        if (Objects.isNull(body))
            return null;
        UserMainPo po = new UserMainPo();
        po.setId(body.getId());
        po.setAppletId(body.getAppletId());
        po.setOrgId(body.getOrgId());
        po.setNickname(body.getNickname());
        po.setDeleted(body.getDeleted());
        po.setCreateTime(body.getCreateTime());
        po.setUpdateTime(body.getUpdateTime());
        return po;
    }

    public static UserMain fromPo(UserMainPo body) {
        if (Objects.isNull(body))
            return null;
        UserMain po = new UserMain();
        po.setId(body.getId());
        po.setAppletId(body.getAppletId());
        po.setOrgId(body.getOrgId());
        po.setNickname(body.getNickname());
        po.setDeleted(body.getDeleted());
        po.setCreateTime(body.getCreateTime());
        po.setUpdateTime(body.getUpdateTime());
        return po;
    }
}