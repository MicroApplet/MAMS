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

package com.asialjim.microapplet.mams.app.infrastructure.datasource.service;

import com.asialjim.microapplet.mams.app.infrastructure.datasource.po.ChlAppPo;
import com.mybatisflex.core.service.IService;

import java.util.List;

/**
 * chl_app 持久化服务
 *
 * @author <a href="mailto:asialjim@hotmail.com">Asial Jim</a>
 * @version 1.0
 * @since 2025/9/19, &nbsp;&nbsp; <em>version:1.0</em>
 */
public interface ChlAppMapperService extends IService<ChlAppPo> {
    /**
     * 按id查询
     *
     * @param id id
     * @return {@link ChlAppPo}
     */
    ChlAppPo queryById(String id);

    /**
     * 按appid查询
     *
     * @param appid appid
     * @return {@link List<ChlAppPo>}
     */
    List<ChlAppPo> queryByAppid(String appid);

    /**
     * 按appid和CHL查询
     *
     * @param appid appid
     * @param chl   渠道代码
     * @return {@link List<ChlAppPo>}
     */
    List<ChlAppPo> queryByAppidAndChl(String appid, String chl);

    /**
     * 按组织id查询
     *
     * @param orgId org id
     * @return {@link List<ChlAppPo>}
     */
    List<ChlAppPo> queryByOrgId(String orgId);


    /**
     * 按CHL和CHL appid查询
     *
     * @param chl      渠道代码
     * @param chlAppid 渠道应用appid
     * @return {@link ChlAppPo}
     */
    ChlAppPo queryByChlAndChlAppid(String chl, String chlAppid);

    /**
     * 按CHL和CHL索引查询
     *
     * @param chl   的背影
     * @param index 指数
     * @return {@link ChlAppPo}
     */
    ChlAppPo queryByChlAndChlIndex(String chl, String index);

    /**
     * 按appid、CHL和CHL appid查询
     *
     * @param appid    appid
     * @param chl      的背影
     * @param chlAppid 的背影appid
     * @return {@link ChlAppPo}
     */
    ChlAppPo queryByAppidAndChlAndChlAppid(String appid, String chl, String chlAppid);

    /**
     * 按CHL查询
     *
     * @param code 代码
     * @return {@link List<ChlAppPo>}
     */
    List<ChlAppPo> queryByChl(String code);
}