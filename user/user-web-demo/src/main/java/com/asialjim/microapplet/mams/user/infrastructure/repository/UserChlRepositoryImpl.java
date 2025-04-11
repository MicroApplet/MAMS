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

package com.asialjim.microapplet.mams.user.infrastructure.repository;

import com.asialjim.microapplet.mams.channel.base.ChlAppType;
import com.asialjim.microapplet.mams.channel.base.ChlType;
import com.asialjim.microapplet.mams.user.infrastructure.cache.UserCache;
import com.asialjim.microapplet.mams.user.infrastructure.repository.datasource.mapper.UserChlBaseMapper;
import com.asialjim.microapplet.mams.user.infrastructure.repository.datasource.po.UserChlPo;
import com.asialjim.microapplet.mams.user.pojo.UserChl;
import com.mybatisflex.core.query.QueryWrapper;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 渠道用户存储
 *
 * @author <a href="mailto:asialjim@hotmail.com">Asial Jim</a>
 * @version 1.0
 * @since 2025/4/11, &nbsp;&nbsp; <em>version:1.0</em>
 */
@Component
public class UserChlRepositoryImpl implements UserChlRepository {
    @Resource
    private UserChlBaseMapper userChlBaseMapper;


    @Override
    @Cacheable(value = UserCache.Name.userChl, key = "#chlType.code +':'+ #chlAppId + ':' + #chlAppType.code + ':' + #userCode")
    public UserChl queryChlUser(ChlType chlType, String chlAppId, ChlAppType chlAppType, String userCode) {
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.where(UserChlPo::getChlType).eq(chlType.getCode());
        wrapper.where(UserChlPo::getChlAppId).eq(chlAppId);
        wrapper.where(UserChlPo::getChlAppType).eq(chlAppType.getCode());
        wrapper.where(UserChlPo::getChlUserId).eq(userCode);
        UserChlPo userChlPo = this.userChlBaseMapper.selectOneByQuery(wrapper);
        return UserChlPo.fromPo(userChlPo);
    }

    @Override
    public String saveAndGetId(UserChl target) {
        UserChlPo po = UserChlPo.toPo(target);
        if (Objects.isNull(po))
            return "";
        this.userChlBaseMapper.insert(po);
        return po.getId();
    }

    @Override
    @Cacheable(value = UserCache.Name.userChl, key = "#userid +':'+ #appletId")
    public List<UserChl> queryByUseridOfApplet(String userid, String appletId) {
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.where(UserChlPo::getUserid).eq(userid);
        wrapper.where(UserChlPo::getAppletId).eq(appletId);
        List<UserChlPo> list = this.userChlBaseMapper.selectListByQuery(wrapper);
        return list.stream().map(UserChlPo::fromPo).collect(Collectors.toList());
    }
}