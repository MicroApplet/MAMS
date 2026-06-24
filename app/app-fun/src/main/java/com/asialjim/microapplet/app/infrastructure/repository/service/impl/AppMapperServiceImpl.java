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

import com.asialjim.microapplet.app.infrastructure.cache.AppCache;
import com.asialjim.microapplet.app.infrastructure.repository.mapper.AppBaseMapper;
import com.asialjim.microapplet.app.infrastructure.repository.po.AppPo;
import com.asialjim.microapplet.app.infrastructure.repository.service.AppMapperService;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class AppMapperServiceImpl
        extends ServiceImpl<AppBaseMapper, AppPo>
        implements AppMapperService {

    @Override
    public Page<AppPo> page(Integer page, Integer size) {
        page = Optional.ofNullable(page).orElse(1);
        size = Optional.ofNullable(size).orElse(10);
        Page<AppPo> objectPage = Page.of(page, size);
        return this.page(objectPage);
    }

    @Override
    @Cacheable(
            value = AppCache.appByPlatformTypeAndAppid,
            key = "#platformTypeCode + ':' + #appid"
    )
    public AppPo queryByPlatformTypeAndAppid(String platformTypeCode, String appid) {
        return queryChain()
                .where(AppPo::getPlatformType).eq(platformTypeCode)
                .where(AppPo::getAppId).eq(appid)
                .one();
    }

    @Override
    @Cacheable(
            value = AppCache.appByAppid,
            key = "#appid"
    )
    public List<AppPo> queryByAppid(String appid) {
        return queryChain().where(AppPo::getAppId).eq(appid).list();
    }
}