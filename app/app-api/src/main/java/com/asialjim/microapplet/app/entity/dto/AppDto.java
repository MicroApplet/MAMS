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
import java.util.List;

/**
 * APP 数据
 *
 * @author Asial Jim
 * @version 1.0
 * @since 2026/3/30, &nbsp;&nbsp; <em>version:1.0</em>
 */
@Data
public class AppDto implements Serializable {
    @Serial
    private static final long serialVersionUID = -292304629509308712L;

    /**
     * 编号
     */
    private String id;
    /**
     * 应用名称
     */
    private String name;
    /**
     * 应用平台类型
     */
    private String platformType;
    /**
     * 应用平台编号
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
     * 主体编号
     */
    private String subjectId;
    /**
     * agent 编号
     */
    private String agentId;
    /**
     * 索引
     */
    private String publicId;
    /**
     * 秘钥
     */
    private String secret;
    /**
     * 是否是 测试/sandbox 环境
     */
    private String sandBox;
    /**
     * 令牌
     */
    private String token;
    /**
     * url
     */
    private String url;
    /**
     * 编码类型: plaintext/cipher
     */
    private String encodeType;
    /**
     * 编码秘钥类型
     */
    private String encodeKeyType;
    /**
     * 编码秘钥值
     */
    private String encodeKeyValue;
    /**
     * 管理员openid
     */
    private String manager;
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

    private List<AppKeyPairDto> keyPairList;
}