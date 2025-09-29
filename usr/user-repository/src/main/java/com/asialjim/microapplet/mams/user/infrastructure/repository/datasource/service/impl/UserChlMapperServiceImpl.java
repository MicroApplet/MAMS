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

package com.asialjim.microapplet.mams.user.infrastructure.repository.datasource.service.impl;

import com.asialjim.microapplet.mams.channel.base.ChlAppType;
import com.asialjim.microapplet.mams.channel.base.ChlType;
import com.asialjim.microapplet.mams.user.infrastructure.cache.UserCacheName;
import com.asialjim.microapplet.mams.user.infrastructure.repository.datasource.mapper.UserChlBaseMapper;
import com.asialjim.microapplet.mams.user.infrastructure.repository.datasource.po.UserChlPo;
import com.asialjim.microapplet.mams.user.infrastructure.repository.datasource.service.UserChlMapperService;
import com.mybatisflex.core.query.QueryChain;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 渠道用户存储服务
 *
 * @author <a href="mailto:asialjim@hotmail.com">Asial Jim</a>
 * @version 1.0
 * @since 2025/4/14, &nbsp;&nbsp; <em>version:1.0</em>
 */
@Component
public class UserChlMapperServiceImpl
        extends ServiceImpl<UserChlBaseMapper, UserChlPo>
        implements UserChlMapperService {

    @Override
    @Cacheable(value = UserCacheName.Name.userChl, key = "#chlType.code +':'+ #chlAppId + ':' + #chlAppType.code + ':' + #chlUserId")
    public UserChlPo queryChlUserByChlUserIdOfChlAppOnChlType(String chlUserId, String chlAppId, ChlAppType chlAppType, ChlType chlType) {
        QueryChain<UserChlPo> wrapper = queryChain();
        wrapper.where(UserChlPo::getChlType).eq(chlType.getCode());
        wrapper.where(UserChlPo::getChlAppId).eq(chlAppId);
        wrapper.where(UserChlPo::getChlAppType).eq(chlAppType.getCode());
        wrapper.where(UserChlPo::getChlUserId).eq(chlUserId);
        return wrapper.one();
    }

    @Override
   // @Cacheable(value = UserCacheName.Name.userChl, key = "#userid +':'+ #appletId")
    public List<UserChlPo> listByUseridOfApplet(String userid, String appletId) {
        QueryChain<UserChlPo> wrapper = queryChain();
        wrapper.where(UserChlPo::getUserid).eq(userid);
        wrapper.where(UserChlPo::getAppletId).eq(appletId);
        return wrapper.list();
    }
}