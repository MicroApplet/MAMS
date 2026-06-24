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

package com.asialjim.microapplet.app.api;

import com.asialjim.microapplet.app.entity.web.AppAgreementVo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface AppAgreementApi {
    String path = "/agreement";

    @GetMapping("/effective")
    AppAgreementVo effectiveAgreement(
            @RequestParam String platformType,
            @RequestParam String appid,
            @RequestParam String type);

    @GetMapping("/effective/list")
    List<AppAgreementVo> effectiveAgreementList(
            @RequestParam String platformType,
            @RequestParam String appid);

}