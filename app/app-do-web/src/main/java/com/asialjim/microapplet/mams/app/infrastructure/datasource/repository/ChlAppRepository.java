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

package com.asialjim.microapplet.mams.app.infrastructure.datasource.repository;

import com.asialjim.microapplet.mams.app.infrastructure.datasource.po.ChlAppPo;
import com.asialjim.microapplet.mams.app.infrastructure.datasource.service.ChlAppMapperService;
import com.asialjim.microapplet.mams.app.vo.ChlAppVo;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * chl app 数仓
 *
 * @author <a href="mailto:asialjim@hotmail.com">Asial Jim</a>
 * @version 1.0
 * @since 2025/9/19, &nbsp;&nbsp; <em>version:1.0</em>
 */
@Slf4j
@Component
public class ChlAppRepository {
    @Resource
    private ChlAppMapperService chlAppMapperService;


    /**
     * 按id查询
     *
     * @param id id
     * @return {@link ChlAppVo}
     */
    public ChlAppVo queryById(String id) {
        ChlAppPo po = this.chlAppMapperService.queryById(id);
        return ChlAppPo.toVo(po);
    }

    /**
     * 按appid查询
     *
     * @param appid appid
     * @return {@link List<ChlAppVo>}
     */
    public List<ChlAppVo> queryByAppid(String appid) {
        List<ChlAppPo> pos = this.chlAppMapperService.queryByAppid(appid);

        return Optional.ofNullable(pos)
                .stream()
                .flatMap(Collection::stream)
                .filter(Objects::nonNull)
                .map(ChlAppPo::toVo)
                .filter(Objects::nonNull)
                .toList();
    }

    /**
     * 按appid和CHL查询
     *
     * @param appid appid
     * @param chl   的背影
     * @return {@link List<ChlAppVo>}
     */
    public List<ChlAppVo> queryByAppidAndChl(String appid, String chl) {
        List<ChlAppPo> pos = this.chlAppMapperService.queryByAppidAndChl(appid, chl);

        return Optional.ofNullable(pos)
                .stream()
                .flatMap(Collection::stream)
                .filter(Objects::nonNull)
                .map(ChlAppPo::toVo)
                .filter(Objects::nonNull)
                .toList();
    }

    /**
     * 按组织id查询
     *
     * @param orgId org id
     * @return {@link List<ChlAppVo>}
     */
    public List<ChlAppVo> queryByOrgId(String orgId) {
        List<ChlAppPo> pos = this.chlAppMapperService.queryByOrgId(orgId);

        return Optional.ofNullable(pos)
                .stream()
                .flatMap(Collection::stream)
                .filter(Objects::nonNull)
                .map(ChlAppPo::toVo)
                .filter(Objects::nonNull)
                .toList();
    }

    /**
     * 按CHL和CHL appid查询
     *
     * @param chl   的背影
     * @param appid appid
     * @return {@link ChlAppVo}
     */
    public ChlAppVo queryByChlAndChlAppid(String chl, String appid) {
        ChlAppPo po = this.chlAppMapperService.queryByChlAndChlAppid(chl, appid);
        return ChlAppPo.toVo(po);
    }

    /**
     * 按CHL和CHL索引查询
     *
     * @param chl   的背影
     * @param index 指数
     * @return {@link ChlAppVo}
     */
    public ChlAppVo queryByChlAndChlIndex(String chl, String index) {
        ChlAppPo po = this.chlAppMapperService.queryByChlAndChlIndex(chl, index);
        return ChlAppPo.toVo(po);
    }

    /**
     * 按appid、CHL和CHL appid查询
     *
     * @param appid    appid
     * @param chl      的背影
     * @param chlAppid 的背影appid
     * @return {@link ChlAppVo}
     */
    public ChlAppVo queryByAppidAndChlAndChlAppid(String appid, String chl, String chlAppid) {
        ChlAppPo po = this.chlAppMapperService.queryByAppidAndChlAndChlAppid(appid, chl, chlAppid);
        return ChlAppPo.toVo(po);
    }

    /**
     * 按CHL查询
     *
     * @param code 代码
     * @return {@link List<ChlAppVo>}
     */
    public List<ChlAppVo> queryByChl(String code) {
        log.info("queryByChl:{}",code);
        List<ChlAppPo> pos = this.chlAppMapperService.queryByChl(code);

        return Optional.ofNullable(pos)
                .stream()
                .flatMap(Collection::stream)
                .filter(Objects::nonNull)
                .map(ChlAppPo::toVo)
                .filter(Objects::nonNull)
                .toList();
    }
}