/*
 *  Copyright 2014-2025 <a href="mailto:asialjim@qq.com">Asial Jim</a>
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *   limitations under the License.
 */

package com.asialjim.microapplet.mams.user.mapper_service.impl;

import com.asialjim.microapplet.mams.user.mapper.IdentificationUserBaseMapper;
import com.asialjim.microapplet.mams.user.mapper_service.IdentificationUserMapperService;
import com.asialjim.microapplet.mams.user.po.IdentificationUserPo;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 证件用户数据仓库
 *
 * @author <a href="mailto:asialjim@hotmail.com">Asial Jim</a>
 * @version 1.0
 * @since 2025/3/13, &nbsp;&nbsp; <em>version:1.0</em>
 */
@Component
public class IdentificationUserMapperServiceImpl
        extends ServiceImpl<IdentificationUserBaseMapper, IdentificationUserPo>
        implements IdentificationUserMapperService {

    @Override
    public List<IdentificationUserPo> queryByUserid(String userId) {
        return queryChain().where(IdentificationUserPo::getUserId).eq(userId).list();
    }

    @Override
    public IdentificationUserPo queryByUseridAndIdType(String userId, String idType) {
        return queryChain().where(IdentificationUserPo::getUserId).eq(userId).where(IdentificationUserPo::getIdType).eq(idType).one();
    }
}