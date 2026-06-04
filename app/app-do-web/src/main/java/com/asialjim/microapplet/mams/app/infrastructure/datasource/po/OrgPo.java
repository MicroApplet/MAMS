/*
 *    Copyright 2014-$year.today <a href="mailto:asialjim@qq.com">Asial Jim</a>
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

package com.asialjim.microapplet.mams.app.infrastructure.datasource.po;

import com.asialjim.microapplet.mams.app.vo.OrgVo;
import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import com.mybatisflex.core.keygen.KeyGenerators;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;


/**
 * org 机构表
 *
 * @author <a href="mailto:asialjim@hotmail.com">Asial Jim</a>
 * @version 1.0
 * @since 2025/9/19, &nbsp;&nbsp; <em>version:1.0</em>
 */
@Data
@Table("org")
@Accessors(chain = true)
public class OrgPo implements Serializable {
    @Serial
    private static final long serialVersionUID = -6865794514347536057L;
    /**
     * 组织机构代码
     */
    @Id(keyType = KeyType.Generator, value = KeyGenerators.snowFlakeId)
    private String id;

    /**
     * 机构名称
     */
    private String name;

    /**
     * 机构证件号码
     */
    private String idNo;

    /**
     * 机构类型
     */
    private String type;

    /**
     * 用户逻辑删除标识
     */
    private Boolean deleted;

    @Column(onInsertValue = "now()")
    private LocalDateTime createTime;

    @Column(onInsertValue = "now()", onUpdateValue = "now()")
    private LocalDateTime updateTime;

    public static OrgVo toVo(OrgPo po){
        OrgVo vo = new OrgVo();
        vo.setId(po.getId());
        vo.setName(po.getName());
        vo.setIdNo(po.getIdNo());
        vo.setType(po.getType());
        vo.setDeleted(po.getDeleted());
        vo.setCreateTime(po.getCreateTime());
        vo.setUpdateTime(po.getUpdateTime());
        return vo;
    }
}