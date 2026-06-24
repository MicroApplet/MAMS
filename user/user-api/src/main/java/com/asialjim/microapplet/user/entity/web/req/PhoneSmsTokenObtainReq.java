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

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.Map;

/**
 * 手机号验证前准备：获取短信验证码请求
 *
 * @author Asial Jim
 * @version 1.0
 * @since 2026/3/5, &nbsp;&nbsp; <em>version:1.0</em>
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PhoneSmsTokenObtainReq implements Serializable {

    @Serial
    private static final long serialVersionUID = 4977435787449159204L;

    /**
     * 开放平台应用用户编号:对应 chl_user 表 id 字段
     */
    private String userCode;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 短信验证码获取类型
     */
    private String tokenType;

    /**
     * 附加参数
     */
    private Map<String, String> params;
}