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

package com.asialjim.microapplet.mams.user.controller;

import com.asialjim.microapplet.mams.user.application.UserIdCardService;
import com.asialjim.microapplet.mams.user.interfaces.endpoint.UserIdCardEndpoint;
import com.asialjim.microapplet.mams.user.pojo.UserIdCard;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * 用户证件服务
 *
 * @author <a href="mailto:asialjim@hotmail.com">Asial Jim</a>
 * @version 1.0
 * @since 2025/4/10, &nbsp;&nbsp; <em>version:1.0</em>
 */
@RestController
@AllArgsConstructor
@RequestMapping("/id-card")
public class UserIdCardController {

    private final UserIdCardEndpoint userIdCardService;


    /**
     * 用户证件注册：通过上传文件
     *
     * @param file {@link MultipartFile file}
     * @return {@link UserIdCard }
     * @since 2025/4/10
     */
    @PostMapping("/reg/by-file")
    public UserIdCard idCardRegByFile(MultipartFile file) {
        return this.userIdCardService.idCardRegByFile(file);
    }
}