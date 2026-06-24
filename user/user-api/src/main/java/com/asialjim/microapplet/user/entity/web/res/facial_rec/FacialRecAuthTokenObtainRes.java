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

package com.asialjim.microapplet.user.entity.web.res.facial_rec;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 人脸核身流水号获取相应结果
 *
 * @author Asial Jim
 * @version 1.0
 * @since 2026/3/9, &nbsp;&nbsp; <em>version:1.0</em>
 */
@Data
public class FacialRecAuthTokenObtainRes implements Serializable {

    @Serial
    private static final long serialVersionUID = -538803531841825749L;

    /**
     * 令牌平台类型
     */
    private String platformType;

    /**
     * 令牌平台应用编号
     */
    private String appid;

    /**
     * 业务流水号:由后台生成
     */
    private String businessNo;

    /**
     * 平台流水号
     */
    private String platformSerial;

    /**
     * 平台生成的用于人脸认证会话的令牌，该令牌被前端调用
     */
    private String token;

    /**
     * 令牌过期时间
     */
    private String expiresIn;
}