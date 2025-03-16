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

import com.asialjim.microapplet.mams.user.parameter.LoginParameter;
import com.asialjim.microapplet.mams.user.vo.LoginParameterVo;

/**
 * 登录参数构建工厂
 *
 * @author <a href="mailto:asialjim@hotmail.com">Asial Jim</a>
 * @version 1.0
 * @since 2025/3/11, &nbsp;&nbsp; <em>version:1.0</em>
 */
public class LoginParameterFactory {

    public static LoginParameter build(String appid,
                                       String chlCode,
                                       String chlAppid,
                                       String chlAppType,
                                       LoginParameterVo body) {
        LoginParameter res = new LoginParameter();
        res.setAppid(appid);
        res.setChlCode(chlCode);
        res.setChlAppid(chlAppid);
        res.setChlAppType(chlAppType);
        res.setUsername(body.getId());
        res.setUserCode(body.getCode());
        return res;
    }
}