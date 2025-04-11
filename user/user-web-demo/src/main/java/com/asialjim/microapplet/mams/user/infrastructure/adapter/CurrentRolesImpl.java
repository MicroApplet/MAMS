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

package com.asialjim.microapplet.mams.user.infrastructure.adapter;

import com.asialjim.microapplet.commons.security.CurrentRoles;
import com.asialjim.microapplet.commons.security.Role;
import com.asialjim.microapplet.commons.security.UserRole;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

/**
 * 当前登录用户所有角色
 *
 * @author <a href="mailto:asialjim@hotmail.com">Asial Jim</a>
 * @version 1.0
 * @since 2025/4/11, &nbsp;&nbsp; <em>version:1.0</em>
 */
@Component
public class CurrentRolesImpl implements CurrentRoles {


    @Override
    public List<Role> hasRole() {
        Boolean function = CurrentUserAdapter.function(sessionUser -> {
            try {
                sessionUser.loginCheck();
                return true;
            } catch (Throwable t) {
                return false;
            }
        });

        // 成功登录后，获取用户角色，此处仅为测试，仅添加已登录用户
        if (Boolean.TRUE.equals(function))
            return Collections.singletonList(UserRole.Login);

        return Collections.emptyList();
    }
}