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

import com.asialjim.microapplet.common.security.MamsSession;
import com.asialjim.microapplet.mams.user.vo.ChlUserVo;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * 用户注册相关 API
 *
 * @author <a href="mailto:asialjim@hotmail.com">Asial Jim</a>
 * @version 1.0
 * @since 2025/9/22, &nbsp;&nbsp; <em>version:1.0</em>
 */
public interface UserRegistrarApi {
    String PATH = "/user/registrar";

    /**
     * 用户注册
     *
     * @param user {@link ChlUserVo user}
     * @return {@link ChlUserVo }
     * @since 2025/9/22
     */
    @PostMapping("/register")
    ChlUserVo register(@RequestBody ChlUserVo user);

    /**
     * 用户注销
     *
     * @param session {@link MamsSession session}
     * @return {@link ChlUserVo }
     * @since 2025/9/22
     */
    @PostMapping("/logoff")
    ChlUserVo logoff(@RequestBody MamsSession session);
}