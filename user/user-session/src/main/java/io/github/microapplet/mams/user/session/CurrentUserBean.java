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
import lombok.Setter;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * 获取当前登录用户信息
 *
 * @author <a href="mailto:asialjim@hotmail.com">Asial Jim</a>
 * @version 1.0
 * @since 2025/3/10, &nbsp;&nbsp; <em>version:1.0</em>
 */
@Setter
@Component(CurrentUserBean.CURRENT_USER_BEAN)
public class CurrentUserBean implements ApplicationContextAware {
    public static final String CURRENT_USER_BEAN = "innerCurrentUserBean";
    private static CurrentUserBean instance;
    private ApplicationContext applicationContext;

    public static SessionUser current() {
        return instance.sessionUser();
    }

    public static User currentUser(){
        return current().user();
    }

    public CurrentUserBean() {
        CurrentUserBean.instance = this;
    }

    public CurrentUserBean(ApplicationContext applicationContext) {
        this();
        this.applicationContext = applicationContext;
    }

    public SessionUser sessionUser() {
        return applicationContext.getBean(SessionUser.sessionUser, SessionUser.class);
    }
}