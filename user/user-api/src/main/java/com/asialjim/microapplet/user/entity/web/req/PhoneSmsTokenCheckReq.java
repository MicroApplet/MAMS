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

import com.fasterxml.jackson.annotation.JsonFormat;
import tools.jackson.databind.annotation.JsonDeserialize;
import tools.jackson.databind.annotation.JsonSerialize;
import tools.jackson.databind.ext.javatime.deser.LocalDateTimeDeserializer;
import tools.jackson.databind.ext.javatime.ser.LocalDateTimeSerializer;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Map;

/**
 * 短信验证码验证手机号
 *
 * @author Asial Jim
 * @version 1.0
 * @since 2026/3/5, &nbsp;&nbsp; <em>version:1.0</em>
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PhoneSmsTokenCheckReq implements Serializable {

    @Serial
    private static final long serialVersionUID = 4977435787449159204L;

    /**
     * 短信验证码获取记录
     */
    private String id;

    /**
     * 开放平台应用用户编号
     */
    @NotBlank(message = "用户编号不能为空")
    private String userid;

    /**
     * 手机号
     */
    @NotBlank(message = "手机号不能为空")
    private String phone;

    @NotBlank(message = "验证码业务类型不能为空")
    private String tokenType;

    /**
     * 验证码
     */
    @NotBlank(message = "验证码不能为空")
    private String smsToken;

    private Map<String, String> params;

    /**
     * 验证时间
     */
    //@NotNull(message = "验证时间不能为空")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS", timezone = "GMT+8")
    private LocalDateTime checkTime;
}