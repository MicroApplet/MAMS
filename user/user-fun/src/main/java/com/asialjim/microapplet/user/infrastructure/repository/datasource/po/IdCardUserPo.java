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

package com.asialjim.microapplet.user.infrastructure.repository.datasource.po;

import com.asialjim.microapplet.user.entity.vo.IdCardUserVo;
import com.asialjim.microapplet.user.infrastructure.util.UserIdGenerator;
import com.fasterxml.jackson.annotation.JsonFormat;
import tools.jackson.databind.annotation.JsonDeserialize;
import tools.jackson.databind.annotation.JsonSerialize;
import tools.jackson.databind.ext.javatime.deser.LocalDateDeserializer;
import tools.jackson.databind.ext.javatime.deser.LocalDateTimeDeserializer;
import tools.jackson.databind.ext.javatime.ser.LocalDateSerializer;
import tools.jackson.databind.ext.javatime.ser.LocalDateTimeSerializer;
import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import io.github.linpeilie.annotations.AutoMapper;
import io.github.linpeilie.annotations.AutoMappers;
import lombok.Data;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.LocalDateTimeTypeHandler;
import org.apache.ibatis.type.LocalDateTypeHandler;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 证件号用户信息表
 *
 * @author Asial Jim
 * @version 1.0
 * @since 2026/3/6, &nbsp;&nbsp; <em>version:1.0</em>
 */
@Data
@Table(IdCardUserPo.tableName)
@AutoMappers(
        @AutoMapper(target = IdCardUserVo.class)
)
public class IdCardUserPo implements Serializable {
    @Serial
    private static final long serialVersionUID = -3147818822568625204L;
    public static final String tableName = "id_card_user";

    /**
     * 主证件用户主键
     */
    @Id(keyType = KeyType.None)
    private String id;

    public String getId() {
        // 生成方案
        // 平台类型 + @ + 应用编号 + : + 证件类型 + # + 证件号.toHexStr()
        return getPlatformType() +
                "@" +
                getAppId() +
                ":" +
                getIdType() +
                "#" +
                UserIdGenerator.sha256WithBase64Url(getIdNumber());
    }

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
     * 证件姓名,应当加密存储, 加密策略应当不影响模糊查询
     * 即： 使用模糊查询时
     * 比如：
     * 明文： 张三    加密后 ->   密文：  werihl wichwl
     * 进行模糊查询时，如果输入匹配的模糊条件时，
     * 模糊查询条件进行加密后，也应当能匹配到对应的密文
     * 如 查询条件为 张* 时，应当能匹配到 密文 werihl 即张三密文的前面部分 如此才不影响模糊查询
     */
    private String name;


    /**
     * 证件类型
     */
    private String idType;

    /**
     * 证件号,应当加密存储
     * 加密存储方案应当与 name 字段相同
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
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(
            jdbcType = JdbcType.DATE,
            typeHandler = LocalDateTypeHandler.class
    )
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
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(
            jdbcType = JdbcType.DATE,
            typeHandler = LocalDateTypeHandler.class
    )
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private LocalDate issueDate;

    /**
     * 证件过期日期
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(
            jdbcType = JdbcType.DATE,
            typeHandler = LocalDateTypeHandler.class
    )
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
    @Column(
            jdbcType = JdbcType.DATE,
            typeHandler = LocalDateTimeTypeHandler.class
    )
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
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(
            jdbcType = JdbcType.DATE,
            typeHandler = LocalDateTimeTypeHandler.class
    )
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS", timezone = "GMT+8")
    private LocalDateTime facialReqAuthTime;

    /**
     * 版本号
     */
    @Column(version = true)
    private Integer version;

    /**
     * 创建时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(
            onInsertValue = "now()",
            jdbcType = JdbcType.DATE,
            typeHandler = LocalDateTimeTypeHandler.class
    )
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS", timezone = "GMT+8")
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(
            onUpdateValue = "now()",
            jdbcType = JdbcType.DATE,
            typeHandler = LocalDateTimeTypeHandler.class
    )
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS", timezone = "GMT+8")
    private LocalDateTime updateTime;
}