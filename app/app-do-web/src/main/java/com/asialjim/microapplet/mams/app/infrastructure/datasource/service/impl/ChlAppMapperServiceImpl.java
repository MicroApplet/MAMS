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

package com.asialjim.microapplet.mams.app.infrastructure.datasource.service.impl;

import com.asialjim.microapplet.mams.app.infrastructure.cache.AppletCache;
import com.asialjim.microapplet.mams.app.infrastructure.datasource.mapper.ChlAppBaseMapper;
import com.asialjim.microapplet.mams.app.infrastructure.datasource.po.ChlAppPo;
import com.asialjim.microapplet.mams.app.infrastructure.datasource.service.ChlAppMapperService;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * chl_app 持久化服务
 *
 * @author <a href="mailto:asialjim@hotmail.com">Asial Jim</a>
 * @version 1.0
 * @since 2025/9/19, &nbsp;&nbsp; <em>version:1.0</em>
 */
@Repository
public class ChlAppMapperServiceImpl extends ServiceImpl<ChlAppBaseMapper, ChlAppPo> implements ChlAppMapperService {

    /**
     * 按id查询
     *
     * @param id id
     * @return {@link ChlAppPo}
     */
    @Override
    @Cacheable(value = AppletCache.Name.chlAppPoById, key = "#id")
    public ChlAppPo queryById(String id) {
        return getById(id);
    }

    /**
     * 按appid查询
     *
     * @param appid appid
     * @return {@link List<ChlAppPo>}
     */
    @Override
    @Cacheable(value = AppletCache.Name.chlAppPoByAppid, key = "#appid")
    public List<ChlAppPo> queryByAppid(String appid) {
        return queryChain()
                .where(ChlAppPo::getAppid).eq(appid)
                .list();
    }

    /**
     * 按appid和CHL查询
     *
     * @param appid appid
     * @param chl   的背影
     * @return {@link List<ChlAppPo>}
     */
    @Override
    @Cacheable(value = AppletCache.Name.chlAppPoByAppidAndChl, key = "#appid + ':' + #chl")
    public List<ChlAppPo> queryByAppidAndChl(String appid, String chl) {
        return queryChain()
                .where(ChlAppPo::getAppid).eq(appid)
                .where(ChlAppPo::getChlType).eq(chl)
                .list();
    }

    /**
     * 按组织id查询
     *
     * @param orgId org id
     * @return {@link List<ChlAppPo>}
     */
    @Override
    @Cacheable(value = AppletCache.Name.chlAppPoByOrgId, key = "#orgId")
    public List<ChlAppPo> queryByOrgId(String orgId) {
        return queryChain()
                .where(ChlAppPo::getOrgId).eq(orgId)
                .list();
    }

    /**
     * 按CHL和CHL appid查询
     *
     * @param chl      渠道代码
     * @param chlAppid 渠道应用appid
     * @return {@link ChlAppPo}
     */
    @Override
    @Cacheable(value = AppletCache.Name.chlAppPoByChlAndChlAppid, key = "#chl + ':' + #chlAppid")
    public ChlAppPo queryByChlAndChlAppid(String chl, String chlAppid) {
        return queryChain()
                .where(ChlAppPo::getChlType).eq(chl)
                .where(ChlAppPo::getChlAppid).eq(chlAppid)
                .one();
    }

    /**
     * 按CHL和CHL索引查询
     *
     * @param chl   的背影
     * @param index 指数
     * @return {@link ChlAppPo}
     */
    @Override
    public ChlAppPo queryByChlAndChlIndex(String chl, String index) {
        return queryChain()
                .where(ChlAppPo::getChlType).eq(chl)
                .where(item -> {
                    item.or(ChlAppPo::getChlAppid).eq(index);
                    item.or(ChlAppPo::getChlSubjectId).eq(index);
                    item.or(ChlAppPo::getChlAgentId).eq(index);
                })
                .one();
    }
}