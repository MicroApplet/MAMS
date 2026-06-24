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

package com.asialjim.microapplet.user.entity.web.req.facial_rec;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 人脸核身流水号获取请求
 *
 * @author Asial Jim
 * @version 1.0
 * @since 2026/3/9, &nbsp;&nbsp; <em>version:1.0</em>
 */
@Data
public class FacialRecAuthTokenObtainReq implements Serializable {
    @Serial
    private static final long serialVersionUID = 8896143494306732085L;

    /**
     * 是否需要人脸图片
     */
    private Boolean facialImageRequire;

    /**
     * 是否需要人脸视频
     */
    private Boolean facialVideoRequire;

    /**
     * 人脸认证主场景
     */
    private String scene;

    /**
     * 人脸认证附加场景
     */
    private String subScene;

    /**
     * 证件姓名
     */
    private String name;

    /**
     * 证件类型
     */
    private String idType;

    /**
     * 证件号码
     */
    private String idNumber;
}