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

package com.asialjim.microapplet.user.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

public interface ChlUserLoginLogApi {
    String path = "/login-log";

    /**
     * 记录登录日志，并返回用户上一次的登录日期
     * 格式： yyyy-MM-dd HH:mm:ss.SSS
     */
    @GetMapping("/recordAndResLastLoginTime")
    String record(@RequestParam String platformType,@RequestParam String appid,@RequestParam String openid);

}