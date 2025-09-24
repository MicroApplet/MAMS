/*
 *    Copyright 2014-2025 <a href="mailto:asialjim@qq.com">Asial Jim</a>
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package com.asialjim.microapplet.mams.user.api;

import com.asialjim.microapplet.common.cons.Headers;
import com.asialjim.microapplet.common.context.Result;
import com.asialjim.microapplet.common.security.MamsSession;
import com.asialjim.microapplet.mams.user.vo.LoginReq;
import com.asialjim.microapplet.web.mvc.annotation.RwIgnore;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 用户认证API
 *
 * @author <a href="mailto:asialjim@hotmail.com">Asial Jim</a>
 * @version 1.0
 * @since 2025/9/22, &nbsp;&nbsp; <em>version:1.0</em>
 */
public interface AuthApi {
    String path = StringUtils.EMPTY;


    /**
     * 登录
     *
     * @return {@link String}
     */
    @RwIgnore
    @PostMapping("/login")
    ResponseEntity<Result<String>> login(@RequestHeader(name = Headers.APP_ID) String appid,
                                         @RequestHeader(name = Headers.APP_CHL) String chl,
                                         @RequestHeader(name = Headers.APP_CHL_APPID) String chlAppid,
                                         @RequestBody LoginReq req);

    /**
     * 身份验证
     *
     * @param token 令牌
     * @return {@link MamsSession}
     */
    @GetMapping("/auth")
    MamsSession auth(@RequestParam(name = Headers.TOKEN) String token);

    /**
     * 登出
     *
	 * @param token {@link String token}
     * @since 2025/9/23
     */
    @DeleteMapping("/logout")
    String logout(@RequestParam(name = Headers.TOKEN) String token);
}