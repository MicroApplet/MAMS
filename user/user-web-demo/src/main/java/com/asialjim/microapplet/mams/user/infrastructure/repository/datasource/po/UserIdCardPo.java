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

import com.asialjim.microapplet.common.human.Gender;
import com.asialjim.microapplet.common.human.IdCardType;
import com.asialjim.microapplet.common.human.Nationality;
import com.asialjim.microapplet.mams.user.pojo.UserIdCard;
import com.asialjim.microapplet.mams.user.pojo.UserMain;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import com.mybatisflex.core.keygen.KeyGenerators;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

/**
 * 用户证件信息
 *
 * @author <a href="mailto:asialjim@hotmail.com">Asial Jim</a>
 * @version 1.0
 * @since 2025/4/10, &nbsp;&nbsp; <em>version:1.0</em>
 */
@Data
@Table("user_id_card")
@Accessors(chain = true)
public class UserIdCardPo implements Serializable {
    private static final long serialVersionUID = 2144032219297234014L;

    /**
     * 主键
     */
    @Id(keyType = KeyType.Generator, value = KeyGenerators.uuid)
    private String id;
    /**
     * 用户编号
     *
     * @see UserMain#getId()
     */
    private String userid;

    /**
     * 用户所属应用
     */
    private String appletId;

    /**
     * 证件号:建议加密存储
     */
    private String idNo;

    /**
     * 证件类型
     */
    private String idType;

    /**
     * 证件姓名
     */
    private String name;

    /**
     * 性别
     */
    private String gender;

    /**
     * 民族
     */
    private String nationality;

    /**
     * 出生日期
     */
    private LocalDate birthday;

    /**
     * 证件地址
     */
    private String address;

    /**
     * 证件签发机关
     */
    private String issueOrg;

    /**
     * 签发日期
     */
    private LocalDate issueDate;

    /**
     * 有效期至/过期时间
     */
    private LocalDate issueExpires;

    /**
     * 证件正面文件编号
     */
    private String frontFileId;

    /**
     * 证件反面文件编号
     */
    private String backFileId;

    /**
     * 创建日期
     */
    private LocalDateTime createTime;

    /**
     * 更新日期
     */
    private LocalDateTime updateTime;

    public static UserIdCardPo toPo(UserIdCard body){
        if (Objects.isNull(body))
            return null;
        UserIdCardPo po = new UserIdCardPo();
        po.setId(body.getId());
        po.setUserid(body.getUserid());
        po.setAppletId(body.getAppletId());
        po.setIdNo(body.getIdNo());
        po.setIdType(Optional.of(body).map(UserIdCard::getIdType).map(IdCardType::getCode).orElse(""));
        po.setName(body.getName());
        po.setGender(Optional.of(body).map(UserIdCard::getGender).map(Gender::getCode).orElse(""));
        po.setNationality(Optional.of(body).map(UserIdCard::getNationality).map(Nationality::getCode).orElse(""));
        po.setBirthday(body.getBirthday());
        po.setAddress(body.getAddress());
        po.setIssueOrg(body.getIssueOrg());
        po.setIssueDate(body.getIssueDate());
        po.setIssueExpires(body.getIssueExpires());
        po.setFrontFileId(body.getFrontFileId());
        po.setBackFileId(body.getBackFileId());
        po.setCreateTime(body.getCreateTime());
        po.setUpdateTime(body.getUpdateTime());
        return po;
    }

    public static UserIdCard fromPo(UserIdCardPo body){
        if (Objects.isNull(body))
            return null;
        UserIdCard po = new UserIdCard();
        po.setId(body.getId());
        po.setUserid(body.getUserid());
        po.setAppletId(body.getAppletId());
        po.setIdNo(body.getIdNo());
        String idType = body.getIdType();
        IdCardType idCardType = IdCardType.codeOf(idType);
        po.setIdType(idCardType);
        po.setName(body.getName());
        po.setGender(Gender.codeOf(body.getGender()));
        po.setNationality(Nationality.codeOf(body.getNationality()));
        po.setBirthday(body.getBirthday());
        po.setAddress(body.getAddress());
        po.setIssueOrg(body.getIssueOrg());
        po.setIssueDate(body.getIssueDate());
        po.setIssueExpires(body.getIssueExpires());
        po.setFrontFileId(body.getFrontFileId());
        po.setBackFileId(body.getBackFileId());
        po.setCreateTime(body.getCreateTime());
        po.setUpdateTime(body.getUpdateTime());
        return po;
    }
}