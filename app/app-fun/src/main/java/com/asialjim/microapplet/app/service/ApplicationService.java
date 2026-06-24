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

package com.asialjim.microapplet.app.service;

import com.asialjim.microapplet.app.entity.web.AppVo;
import com.asialjim.microapplet.app.infrastructure.repository.ApplicationRepository;
import com.asialjim.microapplet.commons.chl.PlatformType;
import com.asialjim.microapplet.commons.standard.page.Page;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ApplicationService {
    private final ApplicationRepository applicationRepository;

    public AppVo queryByPlatformTypeAndAppid(PlatformType platformType,
                                             String appid) {


        String platformTypeCode = platformType.getCode();
        return this.applicationRepository.queryByPlatformTypeAndAppid(platformTypeCode, appid);
    }

    public List<AppVo> queryByAppid(String appid) {
        return this.applicationRepository.queryByAppid(appid);
    }

    public Page<AppVo> list(Integer page, Integer size) {
        return this.applicationRepository.list(page,size);
    }
}