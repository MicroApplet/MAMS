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
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.Map;

/**
 * 短信验证码预验证
 *
 * @author Asial Jim
 * @version 1.0
 * @since 2026/3/5, &nbsp;&nbsp; <em>version:1.0</em>
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SmsTokenPreCheckReq implements Serializable {


    @Serial
    private static final long serialVersionUID = -5259683758306925250L;

    /**
     * 手机号
     */
    @NotBlank(message = "手机号不能为空")
    private String phone;

    @NotBlank(message = "短信验证码不能为空")
    private String smsToken;

    @NotBlank(message = "验证码类型不能为空")
    private String tokenType;

    /**
     * 附加参数
     */
    private Map<String, String> params;
}