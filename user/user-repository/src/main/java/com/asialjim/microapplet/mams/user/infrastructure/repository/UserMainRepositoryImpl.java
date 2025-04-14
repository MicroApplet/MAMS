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

import com.asialjim.microapplet.mams.user.domain.agg.SessionUser;
import com.asialjim.microapplet.mams.user.infrastructure.cache.UserCacheName;
import com.asialjim.microapplet.mams.user.infrastructure.repository.datasource.mapper.UserMainBaseMapper;
import com.asialjim.microapplet.mams.user.infrastructure.repository.datasource.po.UserMainPo;
import com.asialjim.microapplet.mams.user.pojo.UserMain;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * 用户主账户仓库
 *
 * @author <a href="mailto:asialjim@hotmail.com">Asial Jim</a>
 * @version 1.0
 * @since 2025/4/11, &nbsp;&nbsp; <em>version:1.0</em>
 */
@Component
public class UserMainRepositoryImpl implements UserMainRepository {
    @Resource
    private UserMainBaseMapper userMainBaseMapper;

    @Override
    @Cacheable(value = UserCacheName.Name.userMain, key = "#id")
    public UserMain queryById(String id) {
        if (StringUtils.isBlank(id))
            return null;
        UserMainPo po = userMainBaseMapper.selectOneById(id);
        return UserMainPo.fromPo(po);
    }

    @Override
    @Cacheable(value = UserCacheName.Name.userMain, key = "#sessionUser.userid")
    public UserMain queryBySession(SessionUser sessionUser) {
        String userid = sessionUser.getUserid();
        return queryById(userid);
    }

    @Override
    public String saveAndGetId(UserMain userMain) {
        UserMainPo po = UserMainPo.toPo(userMain);
        if (Objects.isNull(po))
            return "";
        this.userMainBaseMapper.insert(po);
        return po.getId();
    }
}
