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

package com.asialjim.microapplet.user.component.facial_rec;


import com.asialjim.microapplet.commons.chl.PlatformType;
import com.asialjim.microapplet.commons.chl.PlatformTypeValue;
import com.asialjim.microapplet.user.code.FacialRecAuthCode;
import com.asialjim.microapplet.user.entity.web.req.facial_rec.FacialRecAuthTokenObtainReq;
import com.asialjim.microapplet.user.entity.web.res.facial_rec.FacialRecAuthTokenObtainRes;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * 人脸认证器
 *
 * @author Asial Jim
 * @version 1.0
 * @since 2026/3/9, &nbsp;&nbsp; <em>version:1.0</em>
 */
public interface FacialRecAuthenticator {

    /**
     * 支持的人脸认证平台
     */
    PlatformType supportPlatformType();

    /**
     * 是否支持获取人脸核身图片
     */
    boolean imageSupport();

    /**
     * 是否支持获取人脸核身视频
     */
    boolean videoSupport();


    /**
     * 获取人脸核身的令牌
     *
     * @param body {@link FacialRecAuthTokenObtainReq body}
     * @return {@link FacialRecAuthTokenObtainRes }
     * @since 2026/3/9
     */
    FacialRecAuthTokenObtainRes obtainToken(FacialRecAuthTokenObtainReq body);


    default boolean support(String platformType) {
        String code = PlatformType.of(platformType).getCode();
        String support = supportPlatformType().getCode();
        return StringUtils.equals(code, support);
    }

    default boolean support(PlatformType platformType) {
        String code = Optional.ofNullable(platformType).map(PlatformType::getCode).orElse(PlatformType.unknown);
        String support = supportPlatformType().getCode();
        return StringUtils.equals(code, support);
    }

    @Component
    class UnSupport implements FacialRecAuthenticator{
        public static final FacialRecAuthenticator INSTANCE = new UnSupport();

        /**
         * 支持的人脸认证平台
         */
        @Override
        public PlatformType supportPlatformType() {
            return PlatformTypeValue.UN_SUPPORT;
        }

        /**
         * 是否支持获取人脸核身图片
         */
        @Override
        public boolean imageSupport() {
            return false;
        }

        /**
         * 是否支持获取人脸核身视频
         */
        @Override
        public boolean videoSupport() {
            return false;
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
            throw FacialRecAuthCode.UnSupportFacialRecAuthPlatformType.ex();
        }
    }
}