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

import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.io.IOUtils;
import tools.jackson.databind.annotation.JsonDeserialize;
import tools.jackson.databind.annotation.JsonSerialize;
import tools.jackson.databind.ext.javatime.deser.LocalDateTimeDeserializer;
import tools.jackson.databind.ext.javatime.ser.LocalDateTimeSerializer;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Base64;

@Data
public class AppKeyPairVo implements Serializable {

    @Serial
    private static final long serialVersionUID = -3634130844232987107L;

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

    public void setCertFileContent(String certFileContent) {
        this.certFileContent = certFileContent;
    }

    public void setCertFileContent(byte[] certFileContent) {
        setCertFileContent(Base64.getEncoder().encodeToString(certFileContent));
    }

    public void setCertFileContent(InputStream inputStream) {
        try {
            byte[] bytes = IOUtils.toByteArray(inputStream);
            setCertFileContent(bytes);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public byte[] certFileContent() {
        if (StringUtils.isBlank(this.certFileContent))
            return new byte[0];
        return Base64.getDecoder().decode(this.certFileContent);
    }

}