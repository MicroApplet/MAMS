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

import java.util.function.Consumer;
import java.util.function.Function;

/**
 * 当前用户组件
 *
 * @author <a href="mailto:asialjim@hotmail.com">Asial Jim</a>
 * @version 1.0
 * @since 2025/3/13, &nbsp;&nbsp; <em>version:1.0</em>
 */
public interface CurrentUserBean {
    String CURRENT_USER_BEAN = "innerCurrentUserBean";

    class Holder {
        public static CurrentUserBean instance;
    }

    static void instance(CurrentUserBean bean) {
        CurrentUserBean.Holder.instance = bean;
    }

    static User current(Consumer<User> consumer) {
        return CurrentUserBean.Holder.instance._current(consumer);
    }

    static void logout() {
        CurrentUserBean.Holder.instance._logout();
    }

    static <Q,R> R function(Function<Q,R> function){
        return Holder.instance._function(function);
    }

    User _current(Consumer<User> consumer);

    <Q, R> R _function(Function<Q, R> function);

    void _logout();
}