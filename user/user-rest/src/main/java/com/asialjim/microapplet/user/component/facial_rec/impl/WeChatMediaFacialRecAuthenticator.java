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

package com.asialjim.microapplet.user.component.facial_rec.impl;


import com.asialjim.microapplet.commons.chl.PlatformType;
import com.asialjim.microapplet.commons.chl.SupportPlatformType;
import com.asialjim.microapplet.user.code.FacialRecAuthCode;
import com.asialjim.microapplet.user.component.facial_rec.FacialRecAuthenticator;
import com.asialjim.microapplet.user.entity.web.req.facial_rec.FacialRecAuthTokenObtainReq;
import com.asialjim.microapplet.user.entity.web.res.facial_rec.FacialRecAuthTokenObtainRes;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 微信人脸认证器
 * <pre>
 *     支持下载人脸媒体文件
 * </pre>
 *
 *
 * @author Asial Jim
 * @version 1.0
 * @since 2026/3/9, &nbsp;&nbsp; <em>version:1.0</em>
 */
@Slf4j
@Component
public class WeChatMediaFacialRecAuthenticator implements FacialRecAuthenticator {
    /**
     * 支持的人脸认证平台
     */
    @Override
    public PlatformType supportPlatformType() {
        return SupportPlatformType.WeChat;
    }

    /**
     * 是否支持获取人脸核身图片
     */
    @Override
    public boolean imageSupport() {
        return true;
    }

    /**
     * 是否支持获取人脸核身视频
     */
    @Override
    public boolean videoSupport() {
        return true;
    }

    /**
     * 获取人脸核身的令牌
     *
     * @param body {@link FacialRecAuthTokenObtainReq body}
     * @return {@link FacialRecAuthTokenObtainRes }
     * @since 2026/3/9
     */
    @Override
    public FacialRecAuthTokenObtainRes obtainToken(FacialRecAuthTokenObtainReq body) {
        log.warn("需要调用微信小程序FaaS，获取人脸核身令牌，暂未实现");
        FacialRecAuthCode.WaitingImplement.thr();
        return null;
    }
}