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

import com.asialjim.microapplet.app.api.AppAgreementApi;
import com.asialjim.microapplet.app.entity.web.AppAgreementVo;
import com.asialjim.microapplet.app.service.AppAgreementService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping(AppAgreementApi.path)
public class AppAgreementController implements AppAgreementApi {
    private final AppAgreementService appAgreementService;

    @Override
    public AppAgreementVo effectiveAgreement(@RequestParam String platformType,
                                             @RequestParam String appid,
                                             @RequestParam String type) {

        return this.appAgreementService.effectiveAgreement(platformType, appid, type);
    }

    @Override
    public List<AppAgreementVo> effectiveAgreementList(@RequestParam String platformType,
                                                       @RequestParam String appid) {

        return this.appAgreementService.effectiveAgreementList(platformType, appid);
    }
}