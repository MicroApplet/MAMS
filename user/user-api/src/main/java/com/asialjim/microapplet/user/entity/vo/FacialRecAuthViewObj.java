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
import tools.jackson.databind.annotation.JsonDeserialize;
import tools.jackson.databind.annotation.JsonSerialize;
import tools.jackson.databind.ext.javatime.deser.LocalDateTimeDeserializer;
import tools.jackson.databind.ext.javatime.ser.LocalDateTimeSerializer;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 人脸核身认证记录
 *
 * @author Asial Jim
 * @version 1.0
 * @since 2026/3/5, &nbsp;&nbsp; <em>version:1.0</em>
 */
@Data
public class FacialRecAuthViewObj implements Serializable {

    @Serial
    private static final long serialVersionUID = -3648220403341983026L;

    /**
     * * 人脸核身记录编号
     */
    private String id;

    /**
     * 人脸核身会话编号
     */
    private String sessionId;

    /**
     * 人脸核身请求编号
     */
    private String traceId;

    /**
     * 人脸核身开放平台类型
     */
    private String platformType;

    /**
     * 人脸核身开放平台编号
     */
    private String platformId;

    /**
     * 人脸核身应用编号
     */
    private String appId;
    /**
     * 人脸核身应用类型
     */
    private String appType;

    /**
     * 人脸核身所属渠道用户
     */
    private String openid;

    /**
     * 人脸核身渠道unionid
     */
    private String unionid;

    /**
     * 人脸核身原数据
     */
    //private Map<String, Object> source;
    private String source;

    /**
     * 人脸核身请求体
     */
    private String request;

    /**
     * 人脸核身响应体
     */
    private String response;

    /**
     * 人脸核身姓名密文
     */
    private String nameCipher;

    /**
     * 人脸核身证件号密文
     */
    private String idNoCipher;

    /**
     * 目标人脸核身姓名密文
     */
    private String targetNameCipher;

    /**
     * 目标人脸核身证件号密文
     */
    private String targetIdNoCipher;

    /**
     * 人脸核身是否成功
     */
    private Boolean matchStatus;

    /**
     * 人脸核身文件编号
     */
    private String fileId;

    /**
     * 人脸核身执行时间
     */
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS", timezone = "GMT+8")
    private LocalDateTime facialRecAuthTime;

    /**
     * 人脸核身时间
     */
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS", timezone = "GMT+8")
    private LocalDateTime createTime;
}