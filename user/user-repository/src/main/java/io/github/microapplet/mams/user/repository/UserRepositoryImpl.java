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

import io.github.microapplet.mams.user.model.User;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * 用户数据仓库
 *
 * @author <a href="mailto:asialjim@hotmail.com">Asial Jim</a>
 * @version 1.0
 * @since 2025/3/11, &nbsp;&nbsp; <em>version:1.0</em>
 */
@Component
public class UserRepositoryImpl implements UserRepository {
    @Override
    public User queryByUserid(String userid) {
        // TODO
        return null;
    }

    @Override
    public User queryByAppidUsernameAndPassword(String appid, String username, String password) {
        User user = new User();
        user.setId("aaa");
        user.setAppid("bbb");
        user.setNickname("ccc");
        user.setUserName("ddd");
        user.setPassword("eee");
        user.setCreateTime(LocalDateTime.now());
        user.setUpdateTime(LocalDateTime.now());

        // TODO
        return user;
    }
}