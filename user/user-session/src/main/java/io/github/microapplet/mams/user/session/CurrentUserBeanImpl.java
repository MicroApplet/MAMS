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

package io.github.microapplet.mams.user.session;

import io.github.microapplet.mams.user.model.User;
import io.github.microapplet.mams.user.res.UserResCode;
import lombok.Setter;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;
import java.util.function.Function;

/**
 * 获取当前登录用户信息
 *
 * @author <a href="mailto:asialjim@hotmail.com">Asial Jim</a>
 * @version 1.0
 * @since 2025/3/10, &nbsp;&nbsp; <em>version:1.0</em>
 */
@Setter
@Component(CurrentUserBean.CURRENT_USER_BEAN)
public class CurrentUserBeanImpl implements CurrentUserBean, ApplicationContextAware {
    private ApplicationContext applicationContext;

    public CurrentUserBeanImpl() {
        CurrentUserBean.instance(this);
    }

    @Override
    public User _current(Consumer<User> consumer) {
        SessionUser sessionUser = sessionUser();
        User user = sessionUser.user(consumer);
        user.setSessionId(sessionUser.getId());
        user.setChlCode(sessionUser.getChlCode());
        user.setChlAppid(sessionUser.getChlAppid());
        user.setChlAppType(sessionUser.getChlAppType());
        user.setUserCode(sessionUser.getUserCode());
        return user;
    }

    @Override
    public <Q, R> R _function(Function<Q, R> function) {
        try {
            SessionUser sessionUser = sessionUser();
            //noinspection unchecked
            return function.apply((Q) sessionUser);
        } catch (Throwable t) {
            return null;
        }
    }

    @Override
    public void _logout() {
        sessionUser().logout();
    }

    private SessionUser sessionUser() {
        try {
            return applicationContext.getBean(SessionUser.sessionUser, SessionUser.class);
        } catch (Throwable t) {
            throw UserResCode.UserNotLogin.bizException();
        }
    }
}