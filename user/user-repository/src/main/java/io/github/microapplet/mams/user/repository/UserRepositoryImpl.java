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

package io.github.microapplet.mams.user.repository;

import io.github.microapplet.mams.user.mapper_service.UserMapperService;
import io.github.microapplet.mams.user.model.User;
import io.github.microapplet.mams.user.po.UserPo;
import io.github.microapplet.mams.user.res.UserResCode;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * 用户数据仓库
 *
 * @author <a href="mailto:asialjim@hotmail.com">Asial Jim</a>
 * @version 1.0
 * @since 2025/3/11, &nbsp;&nbsp; <em>version:1.0</em>
 */
@Component
public class UserRepositoryImpl implements UserRepository {
    @Resource
    private UserMapperService userMapperService;

    @Override
    public User queryByUserid(String userid) {
        UserPo po = userMapperService.queryByUserId(userid);
        return UserPo.fromPo(po);
    }

    @Override
    public User queryByAppidUsername(String appid, String username) {
        UserPo po = userMapperService.queryByAppidAndUsername(appid, username);
        return UserPo.fromPo(po);
    }

    @Override
    public User createInAppid(String appid) {
        UserPo userPo = new UserPo();
        userPo.setAppid(appid);
        this.userMapperService.save(userPo);
        return UserPo.fromPo(userPo);
    }

    @Override
    public User createInAppid(String appid, String username) {
        UserPo userPo = new UserPo();
        userPo.setAppid(appid);
        userPo.setUsername(username);
        this.userMapperService.save(userPo);
        return UserPo.fromPo(userPo);
    }

    @Override
    public User register(User user) {
        UserPo exist = this.userMapperService.queryByAppidAndUsername(user.getAppid(), user.getUsername());
        if (Objects.nonNull(exist))
            UserResCode.UserNameTaken.throwBiz();
        UserPo po = UserPo.toPo(user);
        this.userMapperService.save(po);
        return UserPo.fromPo(po);
    }
}