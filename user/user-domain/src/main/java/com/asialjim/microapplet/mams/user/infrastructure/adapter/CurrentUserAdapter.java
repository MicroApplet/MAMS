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

import com.asialjim.microapplet.mams.user.domain.agg.SessionUser;
import com.asialjim.microapplet.mams.user.pojo.UserMain;

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
public interface CurrentUserAdapter {
    String CURRENT_USER_BEAN = "innerCurrentUserAdaptor";

    class Holder {
        public static CurrentUserAdapter instance;
    }

    static void instance(CurrentUserAdapter bean) {
        CurrentUserAdapter.Holder.instance = bean;
    }

    static SessionUser current(Consumer<SessionUser> consumer, Supplier<RuntimeException> exceptionSupplier) {
        return CurrentUserAdapter.Holder.instance._current(consumer, exceptionSupplier);
    }

    static void logout() {
        CurrentUserAdapter.Holder.instance._logout();
    }

    static <R> R function(Function<SessionUser, R> function) {
        return Holder.instance._function(function);
    }

    SessionUser _current(Consumer<SessionUser> consumer, Supplier<RuntimeException> exceptionSupplier);

    <R> R _function(Function<SessionUser, R> function);

    void _logout();
}