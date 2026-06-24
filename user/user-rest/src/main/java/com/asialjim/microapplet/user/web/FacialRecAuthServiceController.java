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

package com.asialjim.microapplet.user.web;

import com.asialjim.microapplet.user.entity.web.req.facial_rec.FacialRecAuthReq;
import com.asialjim.microapplet.user.entity.web.req.facial_rec.FacialRecAuthTokenObtainReq;
import com.asialjim.microapplet.user.entity.web.res.facial_rec.FacialRecAuthTokenObtainRes;
import com.asialjim.microapplet.user.entity.web.res.facial_rec.FacialRecAuthVo;
import com.asialjim.microapplet.user.service.FacialRecAuthService;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 人脸核身服务
 *
 * @author Asial Jim
 * @version 1.0
 * @since 2026/3/5, &nbsp;&nbsp; <em>version:1.0</em>
 */
@RestController
@AllArgsConstructor
@RequestMapping("/facial-rec-auth")
public class FacialRecAuthServiceController  {

    private final FacialRecAuthService facialRecAuthService;

    /**
     * 获取人脸核身会话令牌
     *
     * @param body {@link FacialRecAuthTokenObtainReq body}
     * @return {@link FacialRecAuthTokenObtainRes }
     * @since 2026/3/9
     */
    @PostMapping("/token/obtain")
    public FacialRecAuthTokenObtainRes obtainToken(FacialRecAuthTokenObtainReq body) {

        return this.facialRecAuthService.obtainToken(body);
    }

    /**
     * 新增人脸核身记录
     *
     * @param body {@link FacialRecAuthReq body}
     * @return {@link FacialRecAuthVo }
     * @since 2026/3/5
     */
    @PostMapping
    public FacialRecAuthVo save(@Validated FacialRecAuthReq body) {
        return this.facialRecAuthService.save(body);
    }
}