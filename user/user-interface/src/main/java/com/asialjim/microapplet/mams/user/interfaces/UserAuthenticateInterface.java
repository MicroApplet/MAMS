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

package com.asialjim.microapplet.mams.user.interfaces;


import com.asialjim.microapplet.mams.user.vo.RegReqVo;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * 用户登录相关的服务
 *
 * @author <a href="mailto:asialjim@hotmail.com">Asial Jim</a>
 * @version 1.0
 * @since 2025/5/23, &nbsp;&nbsp; <em>version:1.0</em>
 */

public interface UserAuthenticateInterface {
    String PATH = "/{appid}/authenticate";


    /**
     * 用户登录
     *
     * @since 2025/4/10
     */
    @SuppressWarnings("MVCPathVariableInspection")
    @PostMapping("/{chlType}/{chlAppId}/{chlAppType}/login")
    String login(@PathVariable("appid") String appid,
                 @PathVariable("chlType") String chlType,
                 @PathVariable("chlAppId") String chlAppId,
                 @PathVariable("chlAppType") String chlAppType,
                 @RequestBody RegReqVo body);

}