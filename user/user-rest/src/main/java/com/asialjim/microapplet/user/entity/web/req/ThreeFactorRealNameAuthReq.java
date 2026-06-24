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

package com.asialjim.microapplet.user.entity.web.req;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.Map;

/**
 * 三要素实名认证
 *
 * @author Asial Jim
 * @version 1.0.0
 * @since 2026/3/5, &nbsp;&nbsp; <em>version:1.0.0</em>
 */
@Setter
@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ThreeFactorRealNameAuthReq implements Serializable {
    @Serial
    private static final long serialVersionUID = -1812828153497770310L;

    /**
     * 用户姓名
     */
    @NotBlank(message = "实名姓名不能为空")
    private String name;

    /**
     * 证件类型
     */
    @NotBlank(message = "证件类型不能为空")
    private String idType;

    /**
     * 证件号
     */
    @NotBlank(message = "证件号码不能为空")
    private String idNumber;

    /**
     * 人脸核身认证数据源
     */
    private String source;

    /**
     * 人脸核身查询请求报文
     */
    //@NotBlank(message = "人脸核身查询请求报文不能为空")
    private String request;

    /**
     * 人脸核身查询响应报文
     */
    //@NotBlank(message = "人脸核身查询响应报文不能为空")
    private String response;


    /**
     * 人脸核身姓名密文
     */
    //@NotBlank(message = "人脸核身结果姓名密文不能为空")
    private String nameCipher;

    /**
     * 人脸核身证件号密文
     */
    //@NotBlank(message = "人脸核身结果证件号密文不能为空")
    private String idNoCipher;

    /**
     * 人脸核身编号
     */
    //@NotBlank(message = "人脸核身文件编号不能为空")
    private String facialRecAuthFileId;

    /**
     * 目标人脸核身姓名密文
     */
    //@NotBlank(message = "人脸核身目标姓名密文不能为空")
    private String targetNameCipher;

    /**
     * 目标人脸核身证件号密文
     */
    //@NotBlank(message = "人脸核身目标证件号密文不能为空")
    private String targetIdNoCipher;

    /**
     * 人脸核身是否成功
     */
    //@NotNull(message = "人脸核身成功标志不能为空")
    private Boolean matchStatus;

    /**
     * 实名手机号
     */
    @NotBlank(message = "实名手机号不能为空")
    private String phone;

    /**
     * 实名手机号短信验证码
     */
    @NotBlank(message = "实名手机验证码不能为空")
    private String smsToken;
}