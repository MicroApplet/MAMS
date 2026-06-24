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

import com.asialjim.microapplet.commons.chl.PlatformType;
import com.asialjim.microapplet.user.entity.vo.UserVo;
import com.asialjim.microapplet.user.infrastructure.util.UserIdGenerator;
import com.fasterxml.jackson.annotation.JsonFormat;
import tools.jackson.databind.annotation.JsonDeserialize;
import tools.jackson.databind.annotation.JsonSerialize;
import tools.jackson.databind.ext.javatime.deser.LocalDateTimeDeserializer;
import tools.jackson.databind.ext.javatime.ser.LocalDateTimeSerializer;
import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import io.github.linpeilie.annotations.AutoMapper;
import io.github.linpeilie.annotations.AutoMappers;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.LocalDateTimeTypeHandler;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Optional;

/**
 * 主用户表
 *
 * @author Asial Jim
 * @version 1.0
 * @since 2026/3/6, &nbsp;&nbsp; <em>version:1.0</em>
 */
@Data
@Table("user")
@AutoMappers(
        @AutoMapper(target = UserVo.class)
)
public class UserPo implements Serializable {
    @Serial
    private static final long serialVersionUID = 5899265695929263402L;

    /**
     * 主用户编号，对应数据库表主键
     */
    @Id(keyType = KeyType.None)
    private String id;

    public String getId() {

        //{渠道短编码}@{platformId}:{sha256(platformId+unionid)}
        return UserIdGenerator.generator(platformType(), platformId(), getUnionid());
    }


    /**
     * 渠道类型
     * 不同的渠道类型用户数据无法互通
     * <ol>
     *     <li>WECHAT: 微信</li>
     *     <li>ALIPAY: 支付宝</li>
     *     <li>douyin: 抖音</li>
     * </ol>
     */
    private String platformType;

    public PlatformType platformType() {
        return PlatformType.of(this.platformType);
    }

    /**
     * 渠道开放平台账号
     */
    private String platformId;

    public String platformId() {
        return Optional.ofNullable(this.platformId).filter(StringUtils::isNotBlank).orElse("_default");
    }

    /**
     * 开放平台 UNIONID
     * 同一个 UNIONID 用户数据相同，可以合并为一个主用户
     * 需要讨论：unionid是否可能会产生变动
     */
    private String unionid;

    /**
     * 主用户昵称
     */
    private String nickname;

    /**
     * 主用户密码
     */
    private String password;

    /**
     * 用户创建时间
     */
    @Column(
            onInsertValue = "now()",
            jdbcType = JdbcType.DATE,
            typeHandler = LocalDateTimeTypeHandler.class
    )
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS", timezone = "GMT+8")
    private LocalDateTime createTime;

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