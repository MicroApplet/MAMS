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

package com.asialjim.microapplet.user.entity.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import tools.jackson.databind.annotation.JsonDeserialize;
import tools.jackson.databind.annotation.JsonSerialize;
import tools.jackson.databind.ext.javatime.deser.LocalDateDeserializer;
import tools.jackson.databind.ext.javatime.deser.LocalDateTimeDeserializer;
import tools.jackson.databind.ext.javatime.ser.LocalDateSerializer;
import tools.jackson.databind.ext.javatime.ser.LocalDateTimeSerializer;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 主证件号用户信息
 *
 * @author Asial Jim
 * @version 1.0
 * @since 2026/3/5, &nbsp;&nbsp; <em>version:1.0</em>
 */
@Data
public class IdCardUserVo implements  Serializable {

    @Serial
    private static final long serialVersionUID = 3907255330432810031L;

    /**
     * 主证件用户主键
     */
    private String id;

    /**
     * 所属主用户编号
     */
    private String userid;

    /**
     * 所属开放平台类型
     */
    private String platformType;

    /**
     * 所属开放平台编号
     */
    private String platformId;

    /**
     * 绑定所属应用编号
     */
    private String appId;

    /**
     * 所属渠道应用openid
     */
    private String openid;

    /**
     * 所属开放平台unionid
     */
    private String unionid;

    /**
     * 证件姓名
     */
    private String name;
    private String nameIdx;

    /**
     * 证件类型
     */
    @NotBlank(message = "证件类型不能为空")
    private String idType;

    /**
     * 证件号
     */
    private String idNumber;

    /**
     * 主手机号
     */
    private String phone;

    /**
     * 证件性别
     */
    private String gender;

    /**
     * 证件国籍
     */
    private String nationality;

    /**
     * 证件生日
     */
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private LocalDate birthday;

    /**
     * 证件地址
     */
    private String address;

    /**
     * 证件发证机关
     */
    private String issue;

    /**
     * 发证时间
     */
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private LocalDate issueDate;

    /**
     * 证件过期日期
     */
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private LocalDate issueExpires;

    /**
     * 证件正面文件地址
     */
    private String frontFileId;

    /**
     * 证件反面文件地址
     */
    private String backFileId;

    /**
     * 证件人脸抠图文件地址
     */
    private String faceFileId;

    /**
     * 最后证件文件上传时间
     */
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS", timezone = "GMT+8")
    private LocalDateTime idCardFileTime;

    /**
     * 最新人脸核身记录编号
     */
    private String facialReqAuthId;

    /**
     * 最新人脸核身时间
     */
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS", timezone = "GMT+8")
    private LocalDateTime facialReqAuthTime;

    /**
     * 版本号
     */
    private Integer version;

    /**
     * 创建时间
     */
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS", timezone = "GMT+8")
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS", timezone = "GMT+8")
    private LocalDateTime updateTime;
}