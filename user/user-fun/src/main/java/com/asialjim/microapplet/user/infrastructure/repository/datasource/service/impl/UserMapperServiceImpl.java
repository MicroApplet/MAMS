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

import com.asialjim.microapplet.spring.App;
import com.asialjim.microapplet.user.infrastructure.cache.UserCache;
import com.asialjim.microapplet.user.infrastructure.repository.datasource.mapper.UserBaseMapper;
import com.asialjim.microapplet.user.infrastructure.repository.datasource.po.UserPo;
import com.asialjim.microapplet.user.infrastructure.repository.datasource.service.UserMapperService;
import com.mybatisflex.core.query.QueryChain;
import com.mybatisflex.core.update.UpdateChain;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.Objects;

@Repository
public class UserMapperServiceImpl
        extends ServiceImpl<UserBaseMapper, UserPo>
        implements UserMapperService {

    @Override
    @Cacheable(value = UserCache.userByUniqId,key = "#platformType + ':' + #platformId + ':' + #unionid" )
    public UserPo queryByPlatformAndUnionid(String platformType, String platformId, String unionid) {
        QueryChain<UserPo> chain = queryChain();
        chain.where(UserPo::getPlatformType).eq(platformType);
        if (StringUtils.isNotBlank(platformId))
            chain.where(UserPo::getPlatformId).eq(platformId);
        chain.where(UserPo::getUnionid).eq(unionid);
        return chain.one();
    }

    @Override
    @CacheEvict(value = UserCache.userByUniqId,key = "#platformType + ':' + #platformId + ':' + #unionid" )
    public void removeByPlatformAndUnionid(String platformType, String platformId, String unionid) {
        UpdateChain<UserPo> chain = updateChain();
        chain.where(UserPo::getPlatformType).eq(platformType);
        if (StringUtils.isNotBlank(platformId))
            chain.where(UserPo::getPlatformId).eq(platformId);
        chain.where(UserPo::getUnionid).eq(unionid);
        chain.remove();
    }

    @Override
    @CacheEvict(value = UserCache.userById, key = "#id")
    public void removeById(String id) {
        App.beanAndThen(UserMapperService.class, service -> {
            UserPo byId = service.getById(id);
            if (Objects.nonNull(byId)) {
                service.removeByPlatformAndUnionid(byId.getPlatformType(), byId.getPlatformId(), byId.getUnionid());
            }
        });
        super.removeById(id);
    }

    @Override
    @Cacheable(value = UserCache.userById,key = "#id")
    public @Nullable UserPo getById(@NonNull Serializable id) {
        return super.getById(id);
    }
}
