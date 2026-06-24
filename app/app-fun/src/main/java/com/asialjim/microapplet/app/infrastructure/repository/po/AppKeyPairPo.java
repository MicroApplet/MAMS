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

import com.asialjim.microapplet.app.entity.web.AppKeyPairVo;
import com.fasterxml.jackson.annotation.JsonFormat;
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

@Data
@Table("app_key_pair")
@AutoMappers(
        @AutoMapper(target = AppKeyPairVo.class)
)
public class AppKeyPairPo implements Serializable {
    @Serial
    private static final long serialVersionUID = 6731068087013058437L;

    /**
     * 秘钥信息
     */
    @Id(keyType = KeyType.Generator, value = KeyGenerators.snowFlakeId)
    private String id;

    /**
     * 开放平台类型
     */
    private String platformType;

    /**
     * 开放平台编号
     */
    private String platformId;

    /**
     * 应用编号
     * @see AppPo#getId()
     */
    private String appId;

    /**
     * 应用类型
     */
    private String appType;

    /**
     * 秘钥类型代码
     */
    private String keyTypeCode;

    /**
     * 秘钥类型名称
     */
    private String keyTypeName;

    /**
     * 加密类型
     */
    private String encType;

    /**
     * 加密秘钥内容
     */
    private String key;

    /**
     * 加密秘钥公钥
     */
    private String pubKey;

    /**
     * 加密秘钥私钥
     */
    private String priKey;

    /**
     * 证书文件内容
     */
    private String certFileContent;
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