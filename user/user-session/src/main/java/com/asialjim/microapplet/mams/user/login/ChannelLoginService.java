/*
 *  Copyright 2014-2025 <a href="mailto:asialjim@qq.com">Asial Jim</a>
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *   limitations under the License.
 */

package com.asialjim.microapplet.mams.user.login;

import com.asialjim.microapplet.mams.user.parameter.LoginParameter;
import com.asialjim.microapplet.mams.user.session.SessionUser;

/**
 * 渠道用户登录服务
 *
 * @author <a href="mailto:asialjim@hotmail.com">Asial Jim</a>
 * @version 1.0
 * @since 2025/3/10, &nbsp;&nbsp; <em>version:1.0</em>
 */
public interface ChannelLoginService {

    /**
     * 登录渠道
     *
     * @return {@link String }
     * @since 2025/3/10
     */
    String chlCode();


    /**
     * 渠道登录
     *
     * @param loginParameter {@link LoginParameter loginParameter}
     * @return {@link SessionUser }
     * @since 2025/3/11
     */
    SessionUser login(LoginParameter loginParameter);
}