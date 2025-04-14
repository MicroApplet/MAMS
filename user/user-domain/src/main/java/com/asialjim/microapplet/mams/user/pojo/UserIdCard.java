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

import com.asialjim.microapplet.common.human.Gender;
import com.asialjim.microapplet.common.human.IdCardType;
import com.asialjim.microapplet.common.human.Nationality;
import lombok.Data;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * 用户证件信息
 *
 * @author <a href="mailto:asialjim@hotmail.com">Asial Jim</a>
 * @version 1.0
 * @since 2025/4/10, &nbsp;&nbsp; <em>version:1.0</em>
 */
@Data
@Accessors(chain = true)
public class UserIdCard implements Serializable {
    private static final long serialVersionUID = 2144032219297234014L;

    /**
     * 主键
     */
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
    private IdCardType idType;

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

    private transient String fileId;
    private transient Boolean front;

    public void setFileId(String fileId) {
        this.fileId = fileId;
        if (Boolean.TRUE.equals(front))
            setFrontFileId(fileId);
        if (Boolean.FALSE.equals(front))
            setBackFileId(fileId);
    }

    public void setFront(boolean front) {
        this.front = front;
        if (front) {
            setFrontFileId(getFileId());
        } else {
            setBackFileId(getFileId());
        }
    }

    private static<T> void populateIfAbsent(Supplier<T> targetCheck, Consumer<T> targetSet,  Supplier<T> source){
        if (Objects.isNull(targetCheck) || Objects.isNull(targetSet) || Objects.isNull(source))
            return;
        if (Objects.isNull(targetCheck.get()))
            targetSet.accept(source.get());
    }

    public void merge(UserIdCard user) {
        //noinspection DuplicatedCode
        populateIfAbsent(this::getId,this::setId,user::getId);
        populateIfAbsent(this::getUserid,this::setUserid,user::getUserid);
        populateIfAbsent(this::getAppletId,this::setAppletId,user::getAppletId);
        populateIfAbsent(this::getIdNo,this::setIdNo,user::getIdNo);
        populateIfAbsent(this::getIdType,this::setIdType,user::getIdType);
        populateIfAbsent(this::getName,this::setName,user::getName);
        populateIfAbsent(this::getGender,this::setGender,user::getGender);
        populateIfAbsent(this::getNationality,this::setNationality,user::getNationality);
        //noinspection DuplicatedCode
        populateIfAbsent(this::getBirthday,this::setBirthday,user::getBirthday);
        populateIfAbsent(this::getAddress,this::setAddress,user::getAddress);
        populateIfAbsent(this::getIssueOrg,this::setIssueOrg,user::getIssueOrg);
        populateIfAbsent(this::getIssueDate,this::setIssueDate,user::getIssueDate);
        populateIfAbsent(this::getIssueExpires,this::setIssueExpires,user::getIssueExpires);
        populateIfAbsent(this::getFrontFileId,this::setFrontFileId,user::getFrontFileId);
        populateIfAbsent(this::getBackFileId,this::setBackFileId,user::getBackFileId);
        populateIfAbsent(this::getCreateTime,this::setCreateTime,user::getCreateTime);
        populateIfAbsent(this::getUpdateTime,this::setUpdateTime,user::getUpdateTime);
    }

    /**
     * 判定证件信息是否可存储
     */
    public boolean idCardSaveAvailable(){
        return StringUtils.isNoneBlank(getIdNo(), getName(), getUserid(), getAppletId()) && Objects.nonNull(getIdType());
    }
}