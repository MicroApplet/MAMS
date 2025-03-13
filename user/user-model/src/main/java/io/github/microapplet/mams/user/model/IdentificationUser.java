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

package io.github.microapplet.mams.user.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import io.github.microapplet.mams.user.cons.Gender;
import io.github.microapplet.mams.user.cons.Nationality;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * 证件用户
 *
 * @author <a href="mailto:asialjim@hotmail.com">Asial Jim</a>
 * @version 1.0
 * @since 2025/3/13, &nbsp;&nbsp; <em>version:1.0</em>
 */
@Data
public class IdentificationUser implements Serializable {
    private static final long serialVersionUID = -978494929467769911L;

    /**
     * 编号
     */
    private String id;

    /**
     * 用户编号
     *
     * @see User#getId()
     */
    private String userId;

    /**
     * 证件号,数据库存储时：加密
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
    private Gender gender;

    /**
     * 民族
     */
    private Nationality nationality;

    /**
     * 出生日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
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
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate issueDate;

    /**
     * 有效期至/过期时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
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
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime createTime;

    /**
     * 更新日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime updateTime;

    public boolean frontAndBack(){
        return StringUtils.isNotBlank(getIdNo()) && Objects.nonNull(getIssueDate());
    }

    private <T> void merge(Supplier<T> getter, Supplier<T> supplier, Consumer<T> setter) {
        if (Objects.isNull(getter.get()))
            setter.accept(supplier.get());
    }

    public void merge(IdentificationUser exist) {
        merge(this::getId, exist::getId, this::setId);
        merge(this::getUserId, exist::getUserId, this::setUserId);
        merge(this::getIdNo, exist::getIdNo, this::setIdNo);
        merge(this::getIdType, exist::getIdType, this::setIdType);
        merge(this::getName, exist::getName, this::setName);
        merge(this::getGender, exist::getGender, this::setGender);
        merge(this::getNationality, exist::getNationality, this::setNationality);
        merge(this::getBirthday, exist::getBirthday, this::setBirthday);
        merge(this::getAddress, exist::getAddress, this::setAddress);
        merge(this::getIssueOrg, exist::getIssueOrg, this::setIssueOrg);
        merge(this::getIssueDate, exist::getIssueDate, this::setIssueDate);
        merge(this::getIssueExpires, exist::getIssueExpires, this::setIssueExpires);
        merge(this::getFrontFileId, exist::getFrontFileId, this::setFrontFileId);
        merge(this::getBackFileId, exist::getBackFileId, this::setBackFileId);
        merge(this::getCreateTime, exist::getCreateTime, this::setCreateTime);
        merge(this::getUpdateTime, exist::getUpdateTime, this::setUpdateTime);
    }
}