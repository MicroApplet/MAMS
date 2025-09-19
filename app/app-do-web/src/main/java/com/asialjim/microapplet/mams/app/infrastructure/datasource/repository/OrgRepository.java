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

import com.asialjim.microapplet.mams.app.infrastructure.datasource.po.OrgPo;
import com.asialjim.microapplet.mams.app.infrastructure.datasource.service.OrgMapperService;
import com.asialjim.microapplet.mams.app.vo.OrgVo;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

/**
 * APP 数仓
 *
 * @author <a href="mailto:asialjim@hotmail.com">Asial Jim</a>
 * @version 1.0
 * @since 2025/9/19, &nbsp;&nbsp; <em>version:1.0</em>
 */
@Component
public class OrgRepository {
    @Resource
    private OrgMapperService orgMapperService;


    /**
     * 按id查询
     *
     * @param id id
     * @return {@link OrgVo}
     */
    public OrgVo queryById(String id) {
        OrgPo po = this.orgMapperService.queryById(id);
        return OrgPo.toVo(po);
    }
}