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

package com.asialjim.microapplet.mams.user.infrastructure.datasource.service.impl;

import com.asialjim.microapplet.mams.user.infrastructure.cache.UserCache;
import com.asialjim.microapplet.mams.user.infrastructure.datasource.mapper.UserBaseMapper;
import com.asialjim.microapplet.mams.user.infrastructure.datasource.po.UserPo;
import com.asialjim.microapplet.mams.user.infrastructure.datasource.service.UserMapperService;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

/**
 * 主用户表持久化服务
 *
 * @author <a href="mailto:asialjim@hotmail.com">Asial Jim</a>
 * @version 1.0
 * @since 2025/9/22, &nbsp;&nbsp; <em>version:1.0</em>
 */
@Repository
public class UserMapperServiceImpl extends ServiceImpl<UserBaseMapper, UserPo> implements UserMapperService {
    /**
     * 按id查询
     *
     * @param userid 用户标识
     * @return {@link UserPo}
     */
    @Override
    @Cacheable(value = UserCache.Name.userPoOf,key = "#userid")
    public UserPo queryById(String userid) {
        return getById(userid);
    }
}