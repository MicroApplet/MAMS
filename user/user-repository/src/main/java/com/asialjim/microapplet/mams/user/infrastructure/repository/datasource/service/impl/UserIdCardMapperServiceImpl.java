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

import com.asialjim.microapplet.common.human.IdCardType;
import com.asialjim.microapplet.mams.user.infrastructure.cache.UserCacheName;
import com.asialjim.microapplet.mams.user.infrastructure.repository.datasource.mapper.UserIdCardBaseMapper;
import com.asialjim.microapplet.mams.user.infrastructure.repository.datasource.po.UserIdCardPo;
import com.asialjim.microapplet.mams.user.infrastructure.repository.datasource.service.UserIdCardMapperService;
import com.mybatisflex.core.query.QueryChain;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

/**
 * 用户证件信息存储服务
 *
 * @author <a href="mailto:asialjim@hotmail.com">Asial Jim</a>
 * @version 1.0
 * @since 2025/4/14, &nbsp;&nbsp; <em>version:1.0</em>
 */
@Component
public class UserIdCardMapperServiceImpl
        extends ServiceImpl<UserIdCardBaseMapper, UserIdCardPo>
        implements UserIdCardMapperService {

    @Override
    @Cacheable(value = UserCacheName.Name.idCardByIdNo, key = "#appletId + ':' + #idCardType.getCode() +':'+ #idNo")
    public UserIdCardPo queryByIdNoOfTypeForApplet(String idNo, IdCardType idCardType, String appletId) {
        //noinspection DuplicatedCode
        QueryChain<UserIdCardPo> wrapper = queryChain();
        wrapper.where(UserIdCardPo::getAppletId).eq(appletId);
        wrapper.where(UserIdCardPo::getIdType).eq(idCardType.getCode());
        wrapper.where(UserIdCardPo::getIdNo).eq(idNo);
        return wrapper.one();
    }

    @Override
    @Cacheable(value = UserCacheName.Name.idCardById, key = "#appletId + ':' + #idCardType.getCode() +':'+ #id")
    public UserIdCardPo queryByIdOfTypeForApplet(String id, IdCardType idCardType, String appletId) {
        //noinspection DuplicatedCode
        QueryChain<UserIdCardPo> wrapper = queryChain();
        wrapper.where(UserIdCardPo::getAppletId).eq(appletId);
        wrapper.where(UserIdCardPo::getIdType).eq(idCardType.getCode());
        wrapper.where(UserIdCardPo::getId).eq(id);
        return wrapper.one();
    }
}