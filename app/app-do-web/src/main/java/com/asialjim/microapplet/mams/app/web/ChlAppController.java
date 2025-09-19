/*
 *    Copyright 2014-$year.today <a href="mailto:asialjim@qq.com">Asial Jim</a>
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

package com.asialjim.microapplet.mams.app.web;

import com.asialjim.microapplet.mams.app.api.ChlAppApi;
import com.asialjim.microapplet.mams.app.infrastructure.datasource.repository.ChlAppRepository;
import com.asialjim.microapplet.mams.app.vo.ChlAppVo;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 渠道 APP 服务
 *
 * @author <a href="mailto:asialjim@hotmail.com">Asial Jim</a>
 * @version 1.0
 * @since 2025/9/18, &nbsp;&nbsp; <em>version:1.0</em>
 */
@RestController
@RequestMapping(ChlAppApi.path)
public class ChlAppController implements ChlAppApi {
    @Resource
    private ChlAppRepository chlAppRepository;


    /**
     * 按id查询
     *
     * @param id id
     * @return {@link ChlAppVo}
     */
    @Override
    public ChlAppVo queryById(String id) {
        return this.chlAppRepository.queryById(id);
    }

    /**
     * 按appid查询
     *
     * @param appid appid
     * @return {@link List<ChlAppVo>}
     */
    @Override
    public List<ChlAppVo> queryByAppid(String appid) {
        return this.chlAppRepository.queryByAppid(appid);
    }

    /**
     * 按appid和CHL查询
     *
     * @param appid appid
     * @param chl   的背影
     * @return {@link List<ChlAppVo>}
     */
    @Override
    public List<ChlAppVo> queryByAppidAndChl(String appid, String chl) {
        return this.chlAppRepository.queryByAppidAndChl(appid, chl);
    }

    /**
     * 按组织id查询
     *
     * @param orgId org id
     * @return {@link List<ChlAppVo>}
     */
    @Override
    public List<ChlAppVo> queryByOrgId(String orgId) {
        return this.chlAppRepository.queryByOrgId(orgId);
    }

    /**
     * 按CHL和CHL appid查询
     *
     * @param chl   的背影
     * @param appid appid
     * @return {@link ChlAppVo}
     */
    @Override
    public ChlAppVo queryByChlAndChlAppid(String chl, String appid) {
        return this.chlAppRepository.queryByChlAndChlAppid(chl,appid);
    }

    /**
     * 按CHL和CHL索引查询
     *
     * @param chl   的背影
     * @param appid appid
     * @return {@link ChlAppVo}
     */
    @Override
    public ChlAppVo queryByChlAndChlIndex(String chl, String appid) {
        return this.chlAppRepository.queryByChlAndChlIndex(chl,appid);
    }
}