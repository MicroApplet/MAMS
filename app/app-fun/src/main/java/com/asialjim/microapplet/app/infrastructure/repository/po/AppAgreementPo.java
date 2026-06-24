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

package com.asialjim.microapplet.app.infrastructure.repository.po;

import com.asialjim.microapplet.app.code.AgreementType;
import com.asialjim.microapplet.app.entity.web.AppAgreementVo;
import com.asialjim.microapplet.commons.standard.utils.JsonUtil;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import com.mybatisflex.core.keygen.KeyGenerators;
import io.github.linpeilie.annotations.AutoMapper;
import io.github.linpeilie.annotations.AutoMappers;
import lombok.Data;
import tools.jackson.databind.annotation.JsonDeserialize;
import tools.jackson.databind.annotation.JsonSerialize;
import tools.jackson.databind.ext.javatime.deser.LocalDateTimeDeserializer;
import tools.jackson.databind.ext.javatime.ser.LocalDateTimeSerializer;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * 应用用户协议
 *
 * @author Asial Jim
 * @version 1.0
 * @since 2026/3/30, &nbsp;&nbsp; <em>version:1.0</em>
 */
@Data
@Table("app_agreement")
@AutoMappers(
        @AutoMapper(target = AppAgreementVo.class)
)
public class AppAgreementPo implements Serializable {
    @Serial
    private static final long serialVersionUID = 7677449543605776069L;
    public static final String TEMPLATE = "template_app_id";

    @Id(keyType = KeyType.Generator, value = KeyGenerators.snowFlakeId)
    private String id;
    private String appid;
    private String agreementCode;
    private String agreementName;

    private String content;

    private String configuration;

    private Long durationMin;

    private Boolean cfcaNeed;

    private String imgUrl;

    /**
     * 生效时间
     */
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS", timezone = "GMT+8")
    private LocalDateTime effectTime;

    /**
     * 失效时间
     */
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS", timezone = "GMT+8")
    private LocalDateTime expiresTime;

    /**
     * 状态
     */
    private String state;

    /**
     * 版本
     */
    @Column(version = true)
    private Integer version;
    /**
     * 版本号
     */
    private String versionName;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS", timezone = "GMT+8")
    private LocalDateTime createTime;
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS", timezone = "GMT+8")
    private LocalDateTime updateTime;

    public static AppAgreementPo create(AgreementType type) {
        AppAgreementPo po = new AppAgreementPo();
        //po.setId("");
        po.setAppid(TEMPLATE);
        po.setAgreementCode(type.getCode());
        po.setAgreementName(type.getName());

        Map<String, String> config = new HashMap<>();
        config.put("name", type.getName());
        config.put("contentType", type.contentType());
        po.setConfiguration(JsonUtil.instance.toStr(config));
        po.setDurationMin(120L);
        po.setCfcaNeed(false);
        po.setImgUrl("");
        po.setVersion(0);
        po.setVersionName("0");
        po.setCreateTime(LocalDateTime.now());
        po.setUpdateTime(LocalDateTime.now());
        return po;
    }
}