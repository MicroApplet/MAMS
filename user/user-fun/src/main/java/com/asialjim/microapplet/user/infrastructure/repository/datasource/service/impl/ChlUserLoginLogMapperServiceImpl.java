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

package com.asialjim.microapplet.user.infrastructure.repository.datasource.service.impl;

import com.asialjim.microapplet.user.infrastructure.repository.datasource.mapper.ChlUserLoginLogBaseMapper;
import com.asialjim.microapplet.user.infrastructure.repository.datasource.po.ChlUserLoginLogPo;
import com.asialjim.microapplet.user.infrastructure.repository.datasource.service.ChlUserLoginLogMapperService;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

@Repository
public class ChlUserLoginLogMapperServiceImpl
        extends ServiceImpl<ChlUserLoginLogBaseMapper, ChlUserLoginLogPo>
        implements ChlUserLoginLogMapperService {

    @Override
    public LocalDateTime lastLoginTime(ChlUserLoginLogPo loginLog) {
        ChlUserLoginLogPo one = queryChain()
                .where(ChlUserLoginLogPo::getPlatformType).eq(loginLog.getPlatformType())
                .where(ChlUserLoginLogPo::getAppid).eq(loginLog.getAppid())
                .where(ChlUserLoginLogPo::getOpenid).eq(loginLog.getOpenid())
                .orderBy(ChlUserLoginLogPo::getLoginTime, false)
                .one();

        return Optional.ofNullable(one)
                .map(ChlUserLoginLogPo::getLoginTime)
                .orElse(null);
    }
}