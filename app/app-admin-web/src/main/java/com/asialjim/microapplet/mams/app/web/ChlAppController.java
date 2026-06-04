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

package com.asialjim.microapplet.mams.app.web;

import com.asialjim.microapplet.common.context.Result;
import com.asialjim.microapplet.commons.security.Role;
import com.asialjim.microapplet.commons.security.RoleCode;
import com.asialjim.microapplet.commons.security.RoleNeed;
import com.asialjim.microapplet.mams.app.api.AppApi;
import com.asialjim.microapplet.mams.app.api.ChlAppApi;
import com.asialjim.microapplet.mams.app.vo.AppVo;
import com.asialjim.microapplet.mams.app.vo.ChlAppVo;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 渠道应用信息管理API
 *
 * @author <a href="mailto:asialjim@hotmail.com">Asial Jim</a>
 * @version 1.0
 * @since 2025/10/13, &nbsp;&nbsp; <em>version:1.0</em>
 */
@RestController
@RequestMapping("/chl-app")
@RequiredArgsConstructor
@RoleNeed(any = {RoleCode.ROOT_BIT,RoleCode.SYSTEM_BIT})
public class ChlAppController {
    private final ChlAppApi chlAppApi;

    @GetMapping("/list/{appid}")
    public List<ChlAppVo> list(@PathVariable("appid") String appid){
        return this.chlAppApi.queryByAppid(appid);
    }

}
