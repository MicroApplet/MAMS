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

package com.asialjim.microapplet.app.infrastructure.repository;

import com.asialjim.microapplet.app.code.AppCode;
import com.asialjim.microapplet.app.entity.web.AppAgreementVo;
import com.asialjim.microapplet.app.entity.web.AppVo;
import com.asialjim.microapplet.app.infrastructure.repository.po.AppAgreementPo;
import com.asialjim.microapplet.app.infrastructure.repository.service.AppAgreementMapperService;
import io.github.linpeilie.Converter;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Slf4j
@Component
@AllArgsConstructor
public class AppAgreementRepository {
    private final AppAgreementMapperService appAgreementMapperService;
    private final Converter converter;
    private final ApplicationRepository applicationRepository;

    public AppAgreementVo effectiveAgreement(String platformType, String appid, String type) {
        AppVo appVo = app(platformType, appid);

        AppAgreementPo po = this.appAgreementMapperService.effectiveAgreement(appVo.getId(), type);
        log.info("查询应用{}的 {} 协议结果：{}", appVo.getId(), type, po);
        if (Objects.isNull(po))
            return null;

        return this.converter.convert(po, AppAgreementVo.class);
    }

    public List<AppAgreementVo> effectiveAgreementList(String platformType, String appid) {
        AppVo appVo = app(platformType, appid);
        List<AppAgreementPo> poList = this.appAgreementMapperService.effectiveAgreementList(appVo.getId());
        if (CollectionUtils.isEmpty(poList))
            return Collections.emptyList();

        return poList.stream().filter(Objects::nonNull)
                .map(item -> converter.convert(item, AppAgreementVo.class))
                .toList();
    }

    private AppVo app(String platformType, String appid) {
        AppVo appVo = applicationRepository.queryByPlatformTypeAndAppid(platformType, appid);
        log.info("查询{}平台{}的应用信息结果：{}", platformType, appid, appVo);
        if (Objects.isNull(appVo) || StringUtils.isBlank(appVo.getId()))
            AppCode.NoSuchAppidErr.thr();
        return appVo;
    }

}
