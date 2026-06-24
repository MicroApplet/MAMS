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

package com.asialjim.microapplet.app.web;

import com.asialjim.microapplet.app.api.ApplicationApi;
import com.asialjim.microapplet.app.entity.web.AppVo;
import com.asialjim.microapplet.app.service.ApplicationService;
import com.asialjim.microapplet.commons.chl.PlatformType;
import com.asialjim.microapplet.commons.chl.SupportPlatformType;
import com.asialjim.microapplet.commons.standard.page.Page;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping(ApplicationApi.path)
public class ApplicationController implements ApplicationApi {
    private final ApplicationService applicationService;

    @Override
    @GetMapping("/query-by-host")
    public AppVo queryByHostAndAppid(@RequestParam  String host,
                                     @RequestParam  String appid) {

        PlatformType platformType = SupportPlatformType.hostOf(host);
        return this.applicationService.queryByPlatformTypeAndAppid(platformType, appid);
    }

    @GetMapping("/list")
    public Page<AppVo> list(@RequestParam Integer page,
                            @RequestParam Integer size) {

        return this.applicationService.list(page, size);
    }

    @Override
    @Validated
    @GetMapping("/query-by-platform-type")
    public AppVo queryByPlatformTypeAndAppid(@RequestParam String platformType, @RequestParam String appid) {
        PlatformType type = PlatformType.of(platformType);
        return this.applicationService.queryByPlatformTypeAndAppid(type, appid);
    }

    @Override
    public List<AppVo> queryByAppid(String appid) {
        return this.applicationService.queryByAppid(appid);
    }
}