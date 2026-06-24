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

import com.asialjim.microapplet.app.entity.web.AppVo;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Table;
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
import java.util.List;

@Data
@Table("app")
@AutoMappers(
        @AutoMapper(target = AppVo.class)
)
public class AppPo implements Serializable {
    @Serial
    private static final long serialVersionUID = -4972920501573110406L;

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
     *
     * @see PlatformPo#getId()
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

    @Column(ignore = true)
    private List<AppKeyPairPo> keyPairList;

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