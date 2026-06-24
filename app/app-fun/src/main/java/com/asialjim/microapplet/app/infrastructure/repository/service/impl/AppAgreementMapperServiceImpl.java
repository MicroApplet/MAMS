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

package com.asialjim.microapplet.app.infrastructure.repository.service.impl;

import com.asialjim.microapplet.app.infrastructure.cache.AppAgreementCache;
import com.asialjim.microapplet.app.infrastructure.repository.mapper.AppAgreementBaseMapper;
import com.asialjim.microapplet.app.infrastructure.repository.po.AppAgreementPo;
import com.asialjim.microapplet.app.infrastructure.repository.service.AppAgreementMapperService;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AppAgreementMapperServiceImpl
        extends ServiceImpl<AppAgreementBaseMapper, AppAgreementPo>
        implements AppAgreementMapperService {

    @Override
    @Cacheable(
            value = AppAgreementCache.appAgreementUniq,
            key = "#appid + ':' + #type"
    )
    public AppAgreementPo effectiveAgreement( String appid, String type) {
        return queryChain()
                .where(AppAgreementPo::getAppid).eq(appid)
                .where(AppAgreementPo::getAgreementCode).eq(type)
                .one();
    }

    @Override
    @Cacheable(
            value = AppAgreementCache.appAgreementAuthorizable,
            key = "#id"
    )
    public List<AppAgreementPo> effectiveAgreementList(String id) {
        return queryChain()
                .where(AppAgreementPo::getAppid).eq(id)
                .list();
    }
}