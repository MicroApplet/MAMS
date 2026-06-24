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

package com.asialjim.microapplet.user.service.login;


import com.asialjim.microapplet.app.entity.web.AppVo;
import com.asialjim.microapplet.commons.chl.PlatformAppType;
import com.asialjim.microapplet.session.LoginReqVo;
import com.asialjim.microapplet.session.Session;

import java.util.Objects;

public interface PlatformAppLoginHandler {
    PlatformAppType supportAppType();

    default boolean support(PlatformAppType appType){
        PlatformAppType exist = this.supportAppType();
        if (Objects.isNull(exist) || Objects.isNull(appType))
            return false;

        return exist.sameWith(appType);
    }

    Session login(String appid, AppVo app, LoginReqVo req);
}