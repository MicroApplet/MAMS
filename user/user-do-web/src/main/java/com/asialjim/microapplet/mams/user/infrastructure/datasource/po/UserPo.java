/*
 *    Copyright 2014-2025 <a href="mailto:asialjim@qq.com">Asial Jim</a>
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package com.asialjim.microapplet.mams.user.infrastructure.datasource.po;

import com.asialjim.microapplet.mams.user.vo.UserVo;
import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import com.asialjim.microapplet.commons.security.Role;
import lombok.Data;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

import static com.asialjim.microapplet.mybatis.flex.MyBatisFlexIdUtils.SNOW_FLAKE_ID_KEY_GENERATOR;

/**
 * 主用户表ORM映射
 *
 * @author <a href="mailto:asialjim@hotmail.com">Asial Jim</a>
 * @version 1.0
 * @since 2025/9/19, &nbsp;&nbsp; <em>version:1.0</em>
 */
@Data
@Table("user")
@Accessors(chain = true)
public class UserPo implements Serializable {
    @Serial
    private static final long serialVersionUID = -596323214539423748L;

    @Id(keyType = KeyType.None)
    private String id;

    public String getId() {
        if (StringUtils.isBlank(this.id))
            this.id = String.valueOf(SNOW_FLAKE_ID_KEY_GENERATOR.nextId());
        return this.id;
    }

    private String appid;
    private String orgId;
    private String nickname;
    private String username;
    private String password;
    private long roleBit;
    private Boolean deleted;
    @Column(onInsertValue = "now()")
    private LocalDateTime createTime;
    @Column(onInsertValue = "now()", onUpdateValue = "now()")
    private LocalDateTime updateTime;

    public void addRoleBit(Long... bits) {
        if (ArrayUtils.isEmpty(bits))
            return;
        for (Long bit : bits) {
            if (Objects.isNull(bit))
                continue;
            if ((roleBit & bit) == bit)
                continue;
            this.roleBit |= roleBit; // 使用按位或添加角色
        }
    }

    public void addRole(Role... roles) {
        if (ArrayUtils.isEmpty(roles))
            return;
        for (Role role : roles) {
            if (Objects.isNull(role))
                continue;
            long bit = role.getBit();
            if ((roleBit & bit) == bit) {
                continue;
            }
            this.roleBit |= bit; // 使用按位或添加角色
        }
    }


    public static UserVo toVo(UserPo po) {
        if (Objects.isNull(po))
            return null;

        final UserVo vo = new UserVo();
        vo.setId(po.getId());
        vo.setAppid(po.getAppid());
        vo.setOrgId(po.getOrgId());
        vo.setNickname(po.getNickname());
        vo.setUsername(po.getUsername());
        vo.setPassword("***");
        vo.setRoleBit(po.getRoleBit());
        vo.setDeleted(po.getDeleted());
        vo.setCreateTime(po.getCreateTime());
        vo.setUpdateTime(po.getUpdateTime());
        return vo;
    }
}