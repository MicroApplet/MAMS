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

package io.github.microapplet.mams.user.mapper_service.impl;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import io.github.microapplet.mams.user.cache.UserCacheConfig;
import io.github.microapplet.mams.user.mapper.UserBaseMapper;
import io.github.microapplet.mams.user.mapper_service.UserMapperService;
import io.github.microapplet.mams.user.po.UserPo;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

/**
 * 用户持久化服务
 *
 * @author <a href="mailto:asialjim@hotmail.com">Asial Jim</a>
 * @version 1.0
 * @since 2025/3/12, &nbsp;&nbsp; <em>version:1.0</em>
 */
@Component
public class UserMapperServiceImpl extends ServiceImpl<UserBaseMapper, UserPo> implements UserMapperService {

    @Override
    @Cacheable(value = UserCacheConfig.Name.BaseUserId, key = "#userid", sync = true)
    public UserPo queryByUserId(String userid) {
        return queryChain().eq(UserPo::getId, userid).one();
    }

    @Override
    @Cacheable(value = UserCacheConfig.Name.AppidAndUsernameAndPassword, key = "#appid and #username and #password", sync = true)
    public UserPo queryByAppidUsernameAndPassword(String appid, String username, String password) {
        return queryChain()
                .where(UserPo::getAppid).eq(appid)
                .and(UserPo::getUsername).eq(username)
                .and(UserPo::getPassword).eq(password).one();
    }
}