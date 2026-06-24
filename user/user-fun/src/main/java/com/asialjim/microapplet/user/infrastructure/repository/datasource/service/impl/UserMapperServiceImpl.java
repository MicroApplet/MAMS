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

import com.asialjim.microapplet.user.infrastructure.repository.datasource.mapper.UserBaseMapper;
import com.asialjim.microapplet.user.infrastructure.repository.datasource.po.UserPo;
import com.asialjim.microapplet.user.infrastructure.repository.datasource.service.UserMapperService;
import com.mybatisflex.core.query.QueryChain;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

@Repository
public class UserMapperServiceImpl
        extends ServiceImpl<UserBaseMapper, UserPo>
        implements UserMapperService {

    @Override
    public UserPo queryByPlatformAndUnionid(String platformType, String platformId, String unionid) {
        QueryChain<UserPo> chain = queryChain();
        chain.where(UserPo::getPlatformType).eq(platformType);
        if (StringUtils.isNotBlank(platformId))
            chain.where(UserPo::getPlatformId).eq(platformId);
        chain.where(UserPo::getUnionid).eq(unionid);
        return chain.one();
    }
}
