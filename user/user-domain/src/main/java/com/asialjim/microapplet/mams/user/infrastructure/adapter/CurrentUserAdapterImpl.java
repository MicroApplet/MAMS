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

import com.asialjim.microapplet.common.application.App;
import com.asialjim.microapplet.mams.user.domain.agg.SessionUser;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;


/**
 * 当前登录用户适配器
 *
 * @author <a href="mailto:asialjim@hotmail.com">Asial Jim</a>
 * @version 1.0
 * @since 2025/4/10, &nbsp;&nbsp; <em>version:1.0</em>
 */
@Component(CurrentUserAdapter.CURRENT_USER_BEAN)
public class CurrentUserAdapterImpl implements CurrentUserAdapter {

    public CurrentUserAdapterImpl() {
        CurrentUserAdapter.instance(this);
    }


    @Override
    public SessionUser _current(Consumer<SessionUser> consumer, Supplier<RuntimeException> exceptionSupplier) {
        SessionUser sessionUser = sessionUser(exceptionSupplier);
        if (Objects.nonNull(consumer))
            consumer.accept(sessionUser);
        return sessionUser;
    }

    @Override
    public <R> R _function(Function<SessionUser, R> function) {
        SessionUser sessionUser = sessionUser(null);
        if (Objects.nonNull(sessionUser) && Objects.nonNull(function))
            return function.apply(sessionUser);
        return null;
    }

    @Override
    public void _logout() {
        SessionUser sessionUser = sessionUser(null);
        if (Objects.nonNull(sessionUser))
            sessionUser.logout();
    }

    private SessionUser sessionUser(Supplier<RuntimeException> exceptionSupplier) {
        return App.beanOrThrow(SessionUser.sessionUser, SessionUser.class, exceptionSupplier);
    }
}