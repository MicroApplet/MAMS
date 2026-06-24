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

import com.asialjim.microapplet.user.infrastructure.cache.IdCardUserCache;
import com.asialjim.microapplet.user.infrastructure.repository.datasource.mapper.IdCardUserBaseMapper;
import com.asialjim.microapplet.user.infrastructure.repository.datasource.po.ChlUserPo;
import com.asialjim.microapplet.user.infrastructure.repository.datasource.po.IdCardUserPo;
import com.asialjim.microapplet.user.infrastructure.repository.datasource.service.IdCardUserMapperService;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import org.springframework.aop.framework.AopContext;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;

@Repository
public class IdCardUserMapperServiceImpl
        extends ServiceImpl<IdCardUserBaseMapper, IdCardUserPo>
        implements IdCardUserMapperService {

    @Override
    @Cacheable(value = IdCardUserCache.idCardUserByChlUserId, key = "#id + ':' +#idType")
    public IdCardUserPo queryByChlUserId(String id, String idType) {
        return queryChain()
                .select(IdCardUserPo.tableName + ".*")
                .from(IdCardUserPo.tableName)
                .leftJoin(ChlUserPo.tableName)
                .on(ChlUserPo::getOpenid, IdCardUserPo::getOpenid)
                .where(IdCardUserPo::getIdType).eq(idType)
                .where(ChlUserPo::getId).eq(id)
                .one();
    }

    @Override
    @Cacheable(
            value = IdCardUserCache.idCardUserByOpenid,
            key = " #platformType + ':' + #appId + ':' + #openid + ':' + #idType"
    )
    public IdCardUserPo queryByPlatformTypeAndAppidAndOpenidAndIdType(String platformType, String appId, String openid, String idType) {
        return queryChain()
                .where(IdCardUserPo::getPlatformType).eq(platformType)
                .where(IdCardUserPo::getAppId).eq(appId)
                .where(IdCardUserPo::getOpenid).eq(openid)
                .where(IdCardUserPo::getIdType).eq(idType)
                .one();
    }

    @Override
    @Caching(evict = {
            @CacheEvict(
                    value = IdCardUserCache.idCardUserByOpenid,
                    key = " #entity.platformType + ':' + #entity.appId + ':' + #entity.openid + ':' + #entity.idType"
            ),
            @CacheEvict(
                    value = IdCardUserCache.idCardUserByOpenid,
                    key = " #entity.platformType + ':' + #entity.appId + ':' + #entity.openid + ':map'"
            ),
            @CacheEvict(value = IdCardUserCache.idCardUserByChlUserId, key = "#entity.id + ':' +#entity.idType")
    })
    public boolean save(IdCardUserPo entity) {
        return super.save(entity);
    }

    @Override
    @Cacheable(
            value = IdCardUserCache.idCardUserByOpenid,
            key = " #platformType + ':' + #appid + ':' + #openid + ':map'"
    )
    public List<IdCardUserPo> queryByPlatformTypeAndAppidAndOpenid(String platformType, String appid, String openid) {
        return queryChain()
                .where(IdCardUserPo::getPlatformType).eq(platformType)
                .where(IdCardUserPo::getAppId).eq(appid)
                .where(IdCardUserPo::getOpenid).eq(openid)
                .list();
    }

    @CacheEvict(value = IdCardUserCache.idCardUserByChlUserId, key = "#chlUserId + ':' + #idType")
    public void clearCacheByChlUserId(String chlUserId, String idType) {
        IdCardUserMapperService mapperService = (IdCardUserMapperService) AopContext.currentProxy();
        IdCardUserPo idCardUserPo = mapperService.queryByChlUserId(chlUserId, idType);
        if (Objects.nonNull(idCardUserPo))
            mapperService.clearCacheByChlOpenid(idCardUserPo.getOpenid(), idType);
    }

    @Caching(
            evict = {
                    @CacheEvict(value = IdCardUserCache.idCardUserByOpenid, key = "#openid + ':' + #idType"),
                    @CacheEvict(value = IdCardUserCache.idCardUserByOpenid, key = "#openid + ':map'")
            }
    )
    public void clearCacheByChlOpenid(String openid, String idType) {
    }
}