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

import com.asialjim.microapplet.mams.user.vo.IdCardUserVo;
import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import com.mybatisflex.core.keygen.KeyGenerators;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * 用户证件信息
 *
 * @author <a href="mailto:asialjim@hotmail.com">Asial Jim</a>
 * @version 1.0
 * @since 2025/9/19, &nbsp;&nbsp; <em>version:1.0</em>
 */
@Data
@Table("id_card_user")
public class IdCardUserPo implements Serializable {
    @Serial
    private static final long serialVersionUID = -3328186345491004306L;

    /**
     * TODO 主键生成方案： MD5(appid) . MD5(userid) . MD5(idType)
     */
    @Id(keyType = KeyType.Generator, value = KeyGenerators.snowFlakeId)
    private String id;
    private String appid;

    /**
     * 主表用户编号
     *
     * @see UserPo#getId()
     */
    private String userid;
    private String idType;

    private String idNo;
    private String name;
    private String gender;
    private String nationality;
    private LocalDate birthday;
    private String address;
    private String issue;
    private LocalDate issueDate;
    private LocalDate issueExpires;
    private String frontFileId;
    private String backFileId;

    private Boolean deleted;

    @Column(onInsertValue = "now()")
    private LocalDateTime createTime;

    @Column(onInsertValue = "now()", onUpdateValue = "now()")
    private LocalDateTime updateTime;

    public static IdCardUserVo toVo(IdCardUserPo po) {
        if (Objects.isNull(po))
            return null;
        IdCardUserVo vo = new IdCardUserVo();
        vo.setId(po.getId());
        vo.setAppid(po.getAppid());
        vo.setUserid(po.getUserid());
        vo.setIdType(po.getIdType());
        vo.setIdNo(po.getIdNo());
        vo.setName(po.getName());
        vo.setGender(po.getGender());
        vo.setNationality(po.getNationality());
        vo.setBirthday(po.getBirthday());
        vo.setAddress(po.getAddress());
        vo.setIssue(po.getIssue());
        vo.setIssueDate(po.getIssueDate());
        vo.setIssueExpires(po.getIssueExpires());
        vo.setFrontFileId(po.getFrontFileId());
        vo.setBackFileId(po.getBackFileId());
        vo.setDeleted(po.getDeleted());
        vo.setCreateTime(po.getCreateTime());
        vo.setUpdateTime(po.getUpdateTime());
        return vo;
    }

    public static IdCardUserPo fromVo(IdCardUserVo source) {
        if (Objects.isNull(source))
            return null;
        IdCardUserPo target = new IdCardUserPo();
        target.setId(source.getId());
        target.setAppid(source.getAppid());
        target.setUserid(source.getUserid());
        target.setIdType(source.getIdType());
        target.setIdNo(source.getIdNo());
        target.setName(source.getName());
        target.setGender(source.getGender());
        target.setNationality(source.getNationality());
        target.setBirthday(source.getBirthday());
        target.setAddress(source.getAddress());
        target.setIssue(source.getIssue());
        target.setIssueDate(source.getIssueDate());
        target.setIssueExpires(source.getIssueExpires());
        target.setFrontFileId(source.getFrontFileId());
        target.setBackFileId(source.getBackFileId());
        target.setDeleted(source.getDeleted());
        target.setCreateTime(source.getCreateTime());
        target.setUpdateTime(source.getUpdateTime());
        return target;
    }
}