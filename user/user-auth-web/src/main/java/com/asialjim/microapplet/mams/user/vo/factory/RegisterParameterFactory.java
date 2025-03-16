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

package com.asialjim.microapplet.mams.user.vo.factory;

import com.asialjim.microapplet.mams.user.parameter.H5RegisterUserParameter;
import com.asialjim.microapplet.mams.user.parameter.RegisterUserParameter;
import com.asialjim.microapplet.mams.user.res.UserSysResCode;
import com.asialjim.microapplet.mams.user.vo.RegisterParameterVo;
import org.apache.commons.lang3.StringUtils;


/**
 * 用户注册参数工厂
 *
 * @author <a href="mailto:asialjim@hotmail.com">Asial Jim</a>
 * @version 1.0
 * @since 2025/3/13, &nbsp;&nbsp; <em>version:1.0</em>
 */
public class RegisterParameterFactory {

    public static RegisterUserParameter create(String appid,
                                               String chlCode,
                                               String chlAppid,
                                               String chlAppType,
                                               RegisterParameterVo body) {
        if (StringUtils.equalsIgnoreCase(chlCode, "H5")) {
            H5RegisterUserParameter userParameter = new H5RegisterUserParameter();
            userParameter.setAppid(appid);
            userParameter.setChlCode(chlCode);
            userParameter.setChlAppid(chlAppid);
            userParameter.setChlAppType(chlAppType);
            userParameter.setNickname(body.getNickname());
            userParameter.setUsername(body.getUsername());
            userParameter.setPassword(body.getPassword());
            return userParameter;
        }
        throw UserSysResCode.UnSupportLoginChannel.bizException();
    }
}