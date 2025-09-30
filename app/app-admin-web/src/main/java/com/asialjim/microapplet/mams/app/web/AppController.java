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
import com.asialjim.microapplet.commons.security.RoleNeed;
import com.asialjim.microapplet.mams.app.api.AppApi;
import com.asialjim.microapplet.mams.app.vo.AppVo;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/app")
@RequiredArgsConstructor
@RoleNeed(any = {Role.Root,Role.System})
public class AppController {
    private final AppApi appApi;

    @GetMapping("/list")
    //@RoleNeed(any = {Role.Root,Role.System})
    public Result<List<AppVo>> list(@RequestParam Long page, @RequestParam Long size){
        return appApi.list(page,size);
    }
}