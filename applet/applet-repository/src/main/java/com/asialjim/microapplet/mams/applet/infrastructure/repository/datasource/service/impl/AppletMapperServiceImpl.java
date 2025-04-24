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

package com.asialjim.microapplet.mams.applet.infrastructure.repository.datasource.service.impl;

import com.asialjim.microapplet.mams.applet.infrastructure.cache.AppletCacheName;
import com.asialjim.microapplet.mams.applet.infrastructure.repository.datasource.mapper.AppletBaseMapper;
import com.asialjim.microapplet.mams.applet.infrastructure.repository.datasource.po.AppletPo;
import com.asialjim.microapplet.mams.applet.infrastructure.repository.datasource.service.AppletMapperService;
import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.core.keygen.KeyGenerators;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryChain;
import com.mybatisflex.core.service.IService;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * 应用信息存储
 *
 * @author <a href="mailto:asialjim@hotmail.com">Asial Jim</a>
 * @version 1.0
 * @since 2025/4/15, &nbsp;&nbsp; <em>version:1.0</em>
 */
@Component
public class AppletMapperServiceImpl
        extends ServiceImpl<AppletBaseMapper, AppletPo>
        implements AppletMapperService {


    @Override
    @Cacheable(value = AppletCacheName.Name.appletById, key = "#appletId")
    public AppletPo queryById(String appletId) {
        return getById(appletId);
    }

    @Override
    public boolean save(AppletPo appletPo) {
        return super.save(appletPo);
    }

    @Override
    public Page<AppletPo> pageOf(Page<AppletPo> page, AppletPo po) {
        QueryChain<AppletPo> chain = queryChain();
        if (Objects.nonNull(po)) {
            if (StringUtils.isNotBlank(po.getId()))
                chain.eq(AppletPo::getId, po.getId());
            if (StringUtils.isNotBlank(po.getName()))
                chain.like(AppletPo::getName, po.getName());
            if (StringUtils.isNotBlank(po.getOrgId()))
                chain.eq(AppletPo::getOrgId, po.getOrgId());
            if (StringUtils.isNotBlank(po.getStatus()))
                chain.eq(AppletPo::getStatus, po.getStatus());
            if (Objects.nonNull(po.getDeleted()))
                chain.eq(AppletPo::getDeleted, po.getDeleted());
        }

        return chain.page(page);
    }
}