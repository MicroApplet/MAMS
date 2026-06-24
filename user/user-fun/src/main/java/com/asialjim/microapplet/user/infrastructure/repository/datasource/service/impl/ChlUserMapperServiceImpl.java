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

import com.asialjim.microapplet.user.infrastructure.cache.ChlUserCache;
import com.asialjim.microapplet.user.infrastructure.repository.datasource.mapper.ChlUserBaseMapper;
import com.asialjim.microapplet.user.infrastructure.repository.datasource.po.ChlUserPo;
import com.asialjim.microapplet.user.infrastructure.repository.datasource.service.ChlUserMapperService;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ChlUserMapperServiceImpl
        extends ServiceImpl<ChlUserBaseMapper, ChlUserPo>
        implements ChlUserMapperService {

    /**
     * 根据主键查询渠道用户信息
     *
     * @param id {@link String id}
     * @return {@link ChlUserPo }
     * @since 2026/3/6
     */
    @Override
    @Cacheable(value = ChlUserCache.chlUserById, key = "#id")
    public ChlUserPo queryById(String id) {
        return getById(id);
    }


    @Override
    @Caching(
            evict = {
                    @CacheEvict(value = ChlUserCache.chlUserById, key = "#entity.id"),
                    @CacheEvict(
                            value = ChlUserCache.chlUserByOpenId,
                            key = "#entity.platformType +':' + #entity.appId + ':' + #entity.openid"
                    ),
                    @CacheEvict(
                            value = ChlUserCache.chlUserByUnionid,
                            key = "#entity.platformType +':' + #entity.appId + ':' + #entity.unionid"
                    )
            }
    )
    public boolean updateById(ChlUserPo entity) {
        return super.updateById(entity);
    }


    @Override
    @Cacheable(
            value = ChlUserCache.chlUserByUnionid,
            key = "#code + ':' + #appId + ':' + #unionid"
    )
    public List<ChlUserPo> queryByPlatformTypeAndAppidAndUnionId(
            String code,
            String appId,
            String unionid) {

        return queryChain()
                .where(ChlUserPo::getPlatformType).eq(code)
                .where(ChlUserPo::getAppId).eq(appId)
                .where(ChlUserPo::getUnionid).eq(unionid)
                .list();
    }

    @Override
    @Cacheable(
            value = ChlUserCache.chlUserByOpenId,
            key = "#platformType +':' + #appid + ':' + #openid"
    )
    public ChlUserPo queryByPlatformTypeAndAppidAndOpenid(
            String platformType,
            String appid,
            String openid) {

        return queryChain()
                .where(ChlUserPo::getPlatformType).eq(platformType)
                .where(ChlUserPo::getAppId).eq(appid)
                .where(ChlUserPo::getOpenid).eq(openid)
                .one();
    }

    @Override
    @Caching(
            evict = {
                    @CacheEvict(value = ChlUserCache.chlUserById, key = "#entity.id"),
                    @CacheEvict(
                            value = ChlUserCache.chlUserByOpenId,
                            key = "#entity.platformType +':' + #entity.appId + ':' + #entity.openid"
                    ),
                    @CacheEvict(
                            value = ChlUserCache.chlUserByUnionid,
                            key = "#entity.platformType +':' + #entity.appId + ':' + #entity.unionid"
                    )
            }
    )
    public boolean save(ChlUserPo entity) {
        return super.save(entity);
    }

    @Override
    public ChlUserPo queryByChlTypeAndOpenid(String chlType, String openid) {
        return queryChain()
                .where(ChlUserPo::getPlatformType).eq(chlType)
                .where(ChlUserPo::getOpenid).eq(openid)
                .one();
    }
}