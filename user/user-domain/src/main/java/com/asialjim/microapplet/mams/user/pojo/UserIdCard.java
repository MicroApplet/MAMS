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

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

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
     * 用户在哪个机构下进行的实名
     *
     * @see UserMain#getOrgId()
     * @see com.asialjim.microapplet.mams.applet.pojo.Applet#getOrgId()
     * @see com.asialjim.microapplet.mams.org.pojo.Organization#getId()
     */
    @SuppressWarnings("JavadocReference")
    private String orgId;

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
}