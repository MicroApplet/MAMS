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

package com.asialjim.microapplet.app.entity.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import tools.jackson.databind.annotation.JsonDeserialize;
import tools.jackson.databind.annotation.JsonSerialize;
import tools.jackson.databind.ext.javatime.deser.LocalDateTimeDeserializer;
import tools.jackson.databind.ext.javatime.ser.LocalDateTimeSerializer;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class AppKeyPairDto implements Serializable {
    @Serial
    private static final long serialVersionUID = 3492742702213216479L;

    /**
     * 秘钥信息
     */
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
     */
    private String appId;
    /**
     * 应用类型
     */
    private String appType;
    /**
     * 秘钥类型
     */
    private String type;
    /**
     * 加密类型
     */
    private String keyType;
    /**
     * 加密秘钥内容
     */
    private String value;
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
    private byte[] certFileContent;
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