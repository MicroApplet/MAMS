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

package com.asialjim.microapplet.app.entity.web;

import com.asialjim.microapplet.commons.standard.utils.JsonUtil;
import com.fasterxml.jackson.annotation.JsonFormat;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.annotation.JsonDeserialize;
import tools.jackson.databind.annotation.JsonSerialize;
import tools.jackson.databind.ext.javatime.deser.LocalDateTimeDeserializer;
import tools.jackson.databind.ext.javatime.ser.LocalDateTimeSerializer;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * 应用用户协议
 *
 * @author Asial Jim
 * @version 1.0
 * @since 2026/3/30, &nbsp;&nbsp; <em>version:1.0</em>
 */
@Data
public class AppAgreementVo implements Serializable {
    @Serial
    private static final long serialVersionUID = 2933435800232785870L;

    /**
     * 协议数据库编号
     */
    private String id;

    /**
     * 平台类型
     */
    private String platformType;

    /**
     * 协议所属应用
     */
    private String appid;
    /**
     * 协议类型编号
     */
    private String agreementCode;
    /**
     * 协议类型名称
     */
    private String agreementName;

    /**
     * 协议内容
     */
    private String content;

    /**
     * 配置信息
     */
    private String configuration;

    /**
     * 内容类型：
     * 1: url http/https 链接
     * 2: base64 以 base64 编码的二进制数据字符串
     * 3: base64url 以 base64url 编码的二进制数据字符串
     * 4：text 纯文本数据
     */
    private String contentType;


    /**
     * 授权有效期时间，单位：秒
     */
    private Long durationMin;

    /**
     * 是否需要CFCA签章
     */
    private Boolean cfcaNeed;

    /**
     * 协议图片链接
     */
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
    private Integer version;

    /**
     * 版本号
     */
    private String versionName;

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


    /**
     * 内容类型：
     * 1: url http/https 链接
     * 2: base64 以 base64 编码的二进制数据字符串
     * 3: base64url 以 base64url 编码的二进制数据字符串
     * 4：text 纯文本数据
     */
    @SuppressWarnings("unused")
    public String getContentType() {
        if (StringUtils.isBlank(this.configuration))
            return StringUtils.EMPTY;

        try {
            JsonNode tree = JsonUtil.instance.toTree(this.configuration);
            JsonNode jsonNode = tree.get("contentType");
            if (Objects.nonNull(jsonNode))
                return jsonNode.asString();
        } catch (Throwable t) {
            return StringUtils.EMPTY;
        }
        return StringUtils.EMPTY;
    }
}