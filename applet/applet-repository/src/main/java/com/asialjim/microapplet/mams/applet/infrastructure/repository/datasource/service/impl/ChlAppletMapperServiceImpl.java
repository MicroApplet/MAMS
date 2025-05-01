/*
 *  Copyright 2014-2025 <a href="mailto:asialjim@qq.com">Asial Jim</a>
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *   limitations under the License.
 */

package com.asialjim.microapplet.mams.applet.infrastructure.repository.datasource.service.impl;

import com.asialjim.microapplet.mams.applet.infrastructure.cache.AppletCacheName;
import com.asialjim.microapplet.mams.applet.infrastructure.repository.datasource.mapper.ChlAppletBaseMapper;
import com.asialjim.microapplet.mams.applet.infrastructure.repository.datasource.po.ChlAppletPo;
import com.asialjim.microapplet.mams.applet.infrastructure.repository.datasource.service.ChlAppletMapperService;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 渠道应用存储服务
 *
 * @author <a href="mailto:asialjim@hotmail.com">Asial Jim</a>
 * @version 1.0
 * @since 2025/4/15, &nbsp;&nbsp; <em>version:1.0</em>
 */
@Component
public class ChlAppletMapperServiceImpl
        extends ServiceImpl<ChlAppletBaseMapper, ChlAppletPo>
        implements ChlAppletMapperService {

    @Override
    @Cacheable(value = AppletCacheName.Name.chlAppletsByAppletId, key = "#appletId")
    public List<ChlAppletPo> listByAppletId(String appletId) {
        return queryChain().where(ChlAppletPo::getAppletId).eq(appletId).list();
    }

    @Override
    @Cacheable(value = AppletCacheName.Name.chlAppletByChlAppId, key = "#chlAppId")
    public ChlAppletPo queryByChlAppId(String chlAppId) {
        return queryChain()
                .where(ChlAppletPo::getChlAppId).eq(chlAppId)
                .one();
    }

    @Override
    @Cacheable(value = AppletCacheName.Name.chlAppletById, key = "#id")
    public ChlAppletPo queryById(String id) {
        return super.getById(id);
    }

    @Override
    @Cacheable(value = AppletCacheName.Name.chlAppletByIndex, key = "#index")
    public ChlAppletPo queryBySubjectId(String index) {
        return queryChain().where(ChlAppletPo::getChlSubjectId).eq(index).one();
    }

    @Override
    @Cacheable(value = AppletCacheName.Name.chlAppletByIndex, key = "#appid + ':' + #appTypeCode")
    public ChlAppletPo queryByChlAppidAndType(String appid, String appTypeCode) {
        return queryChain().where(ChlAppletPo::getChlAppId).eq(appid)
                .where(ChlAppletPo::getChlAppType).eq(appTypeCode).one();
    }
}