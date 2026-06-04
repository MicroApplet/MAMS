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

package com.asialjim.microapplet.mams.user.infrastructure.datasource.repository;

import com.asialjim.microapplet.mams.user.infrastructure.datasource.po.ChlUserPo;
import com.asialjim.microapplet.mams.user.infrastructure.datasource.service.ChlUserMapperService;
import com.asialjim.microapplet.mams.user.vo.ChlUserVo;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryChain;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * 渠道用户数仓
 *
 * @author <a href="mailto:asialjim@hotmail.com">Asial Jim</a>
 * @version 1.0
 * @since 2025/9/22, &nbsp;&nbsp; <em>version:1.0</em>
 */
@Slf4j
@Component
@AllArgsConstructor
public class ChlUserRepository {
    private final ChlUserMapperService chlUserMapperService;


    /**
     * 查询条件：CHL类型CHL appid CHL应用类型和CHL用户id
     *
     * @param chlType    的背影类型
     * @param chlAppId   CHL app id
     * @param chlAppType CHL应用类型
     * @param chlUserId  CHL用户id
     * @return {@link ChlUserPo}
     */
    public ChlUserPo queryByChlTypeChlAppidChlAppTypeAndChlUserId(String chlType,
                                                                  String chlAppId,
                                                                  String chlAppType,
                                                                  String chlUserId) {

        return this.chlUserMapperService.queryByChlTypeChlAppidChlAppTypeAndChlUserId(chlType, chlAppId, chlAppType, chlUserId);
    }


    /**
     * 保存
     *
     * @param po 阿宝
     */
    public void save(ChlUserPo po) {
        this.chlUserMapperService.save(po);
    }

    public ChlUserVo save(ChlUserVo vo) {
        ChlUserPo po = ChlUserPo.fromVo(vo);
        this.chlUserMapperService.save(po);
        return ChlUserPo.toVo(po);
    }

    /**
     * 更新
     *
     * @param po 阿宝
     */
    public void update(ChlUserPo po) {
        if (Objects.isNull(po))
            return;

        if (StringUtils.isBlank(po.getId())) {
            log.warn("未指定渠道用户主键,无法更新用户信息:{}", po);
            return;
        }

        boolean b = this.chlUserMapperService.updateById(po);
        if (b)
            this.chlUserMapperService.deleteById(po.getId());
    }

    /**
     * 按用户id查询
     *
     * @param id id
     * @return {@link List<ChlUserPo>}
     */
    public List<ChlUserVo> queryByUserId(String id) {
        List<ChlUserPo> pos = this.chlUserMapperService.queryByUserid(id);
        if (CollectionUtils.isEmpty(pos))
            return Collections.emptyList();
        return pos.stream()
                .filter(Objects::nonNull)
                .map(ChlUserPo::toVo)
                .toList();
    }

    public ChlUserVo queryById(String root) {
        ChlUserPo po = this.chlUserMapperService.queryById(root);
        return ChlUserPo.toVo(po);
    }

    public ChlUserPo queryByUserIdAndChlAndChlAppidAndChlAppType(String userid, String chl, String chlAppid, String chlAppType) {
        return this.chlUserMapperService.queryByUserIdAndChlAndChlAppidAndChlAppType(userid, chl, chlAppid, chlAppType);
    }

    public List<String> queryUseridByChlAppidTypeForAppid(String chl, String chlAppid, String chlAppType, String appid) {
        QueryChain<ChlUserPo> chain = chlUserMapperService.queryChain();
        if (StringUtils.isNotBlank(chl))
            chain.where(ChlUserPo::getChlType).eq(chl);
        if (StringUtils.isNotBlank(chlAppid))
            chain.where(ChlUserPo::getChlAppid).eq(chlAppid);
        if (StringUtils.isNotBlank(chlAppType))
            chain.where(ChlUserPo::getChlAppType).eq(chlAppType);
        if (StringUtils.isNotBlank(appid))
            chain.where(ChlUserPo::getAppid).eq(appid);
        //noinspection unchecked
        return chain.select(ChlUserPo::getUserid)
                .pageAs(Page.of(1, 1000), String.class)
                .getRecords();
    }
}