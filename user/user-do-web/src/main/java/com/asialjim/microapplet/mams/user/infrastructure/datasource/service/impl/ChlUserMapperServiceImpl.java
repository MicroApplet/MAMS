/*
 *    Copyright 2014-2025 <a href="mailto:asialjim@qq.com">Asial Jim</a>
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package com.asialjim.microapplet.mams.user.infrastructure.datasource.service.impl;

import com.asialjim.microapplet.mams.user.infrastructure.cache.UserCache;
import com.asialjim.microapplet.mams.user.infrastructure.datasource.mapper.ChlUserBaseMapper;
import com.asialjim.microapplet.mams.user.infrastructure.datasource.po.ChlUserPo;
import com.asialjim.microapplet.mams.user.infrastructure.datasource.service.ChlUserMapperService;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.aop.framework.AopContext;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;

/**
 * 渠道用户持久化
 *
 * @author <a href="mailto:asialjim@hotmail.com">Asial Jim</a>
 * @version 1.0
 * @since 2025/9/22, &nbsp;&nbsp; <em>version:1.0</em>
 */
@Slf4j
@Repository
public class ChlUserMapperServiceImpl extends ServiceImpl<ChlUserBaseMapper, ChlUserPo> implements ChlUserMapperService {

    /**
     * 查询条件：CHL类型CHL appid CHL应用类型和CHL用户id
     *
     * @param chlType    渠道类型
     * @param chlAppId   CHL app id
     * @param chlAppType CHL应用类型
     * @param chlUserId  CHL用户id
     * @return {@link ChlUserPo}
     */
    @Override
    @Cacheable(value = UserCache.Name.chlUserPoOf, key = "#chlType + ':' + #chlAppId + ':' + #chlAppType + ':' + #chlUserId")
    public ChlUserPo queryByChlTypeChlAppidChlAppTypeAndChlUserId(String chlType,
                                                                  String chlAppId,
                                                                  String chlAppType,
                                                                  String chlUserId) {

        return queryChain()
                .where(ChlUserPo::getChlType).eq(chlType)
                .where(ChlUserPo::getChlAppid).eq(chlAppId)
                .where(ChlUserPo::getChlAppType).eq(chlAppType)
                .where(ChlUserPo::getChlUserid).eq(chlUserId)
                .one();
    }

    /**
     * 按id查询
     *
     * @param id id
     * @return {@link ChlUserPo}
     */
    @Override
    @Cacheable(value = UserCache.Name.chlUserPoOf, key = "#id")
    public ChlUserPo queryById(String id) {
        if (StringUtils.isBlank(id))
            return null;
        return getById(id);
    }

    /**
     * 按id删除
     *
     * @param id id
     */
    @Override
    @CacheEvict(value = UserCache.Name.chlUserPoOf, key = "#id")
    public void deleteById(String id) {
        if (StringUtils.isBlank(id))
            return;
        ChlUserMapperService service = (ChlUserMapperService) AopContext.currentProxy();
        ChlUserPo exist = service.queryById(id);
        if (Objects.isNull(exist))
            return;

        service.deleteByChlTypeChlAppidChlAppTypeAndChlUserId(exist);
        service.removeById(id);
    }


    /**
     * 按CHL类型CHL appid CHL应用类型和CHL用户id删除
     *
     * @param po 阿宝
     */
    @Override
    @CacheEvict(value = UserCache.Name.chlUserPoOf, key = "#po.chlType + ':' + #po.chlAppid + ':' + #po.chlAppType + ':' + #po.chlUserid")
    public void deleteByChlTypeChlAppidChlAppTypeAndChlUserId(ChlUserPo po) {
        updateChain()
                .where(ChlUserPo::getChlType).eq(po.getChlType())
                .where(ChlUserPo::getChlAppid).eq(po.getChlAppid())
                .where(ChlUserPo::getChlAppType).eq(po.getChlAppid())
                .where(ChlUserPo::getChlUserid).eq(po.getChlUserid())
                .remove();
    }

    /**
     * 按用户id查询
     *
     * @param id id
     * @return {@link List<ChlUserPo>}
     */
    @Override
    //@Cacheable(value = UserCache.Name.chlUserPosOfUserid, key = "#id")
    public List<ChlUserPo> queryByUserid(String id) {
        return queryChain().where(ChlUserPo::getUserid).eq(id).list();
    }
}