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

package com.asialjim.microapplet.mams.user.infrastructure.datasource.service;

import com.asialjim.microapplet.mams.user.infrastructure.datasource.po.ChlUserPo;
import com.mybatisflex.core.service.IService;

import java.util.List;

/**
 * 渠道用户持久化服务
 *
 * @author <a href="mailto:asialjim@hotmail.com">Asial Jim</a>
 * @version 1.0
 * @since 2025/9/22, &nbsp;&nbsp; <em>version:1.0</em>
 */
public interface ChlUserMapperService extends IService<ChlUserPo> {

    /**
     * 查询条件：CHL类型CHL appid CHL应用类型和CHL用户id
     *
     * @param chlType    的背影类型
     * @param chlAppId   CHL app id
     * @param chlAppType CHL应用类型
     * @param chlUserId  CHL用户id
     * @return {@link ChlUserPo}
     */
    ChlUserPo queryByChlTypeChlAppidChlAppTypeAndChlUserId(
            String chlType,
            String chlAppId,
            String chlAppType,
            String chlUserId);

    /**
     * 按id查询
     *
     * @param id id
     * @return {@link ChlUserPo}
     */
    ChlUserPo queryById(String id);

    /**
     * 按id删除
     *
     * @param id id
     */
    void deleteById(String id);

    /**
     * 按CHL类型CHL appid CHL应用类型和CHL用户id删除
     *
     * @param po 阿宝
     */
    void deleteByChlTypeChlAppidChlAppTypeAndChlUserId(ChlUserPo po);


    /**
     * 按用户id查询
     *
     * @param id id
     * @return {@link List<ChlUserPo>}
     */
    List<ChlUserPo> queryByUserid(String id);
}
