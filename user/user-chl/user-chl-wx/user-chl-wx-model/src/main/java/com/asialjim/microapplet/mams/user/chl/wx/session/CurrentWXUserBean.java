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

package com.asialjim.microapplet.mams.user.chl.wx.session;

import com.asialjim.microapplet.mams.user.chl.wx.model.WeChatChlUser;
import com.asialjim.microapplet.mams.user.session.MamsSession;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * 当前微信用户组件
 *
 * @author <a href="mailto:asialjim@hotmail.com">Asial Jim</a>
 * @version 1.0
 * @since 2025/3/26, &nbsp;&nbsp; <em>version:1.0</em>
 */
public interface CurrentWXUserBean {
    String CURRENT_WX_USER_BEAN = "innerCurrentWXUserBean";

    class Holder {
        public static CurrentWXUserBean instance;
    }

    static void instance(CurrentWXUserBean bean) {
        CurrentWXUserBean.Holder.instance = bean;
    }

    static List<WeChatChlUser> current(Consumer<List<WeChatChlUser>> consumer) {
        return Holder.instance._current(consumer);
    }

    static WeChatChlUser currentWeChatChlUser() {
        return Holder.instance._currentWeChatChlUser();
    }

    static <R> R function(Function<MamsSession, R> function) {
        return Holder.instance._function(function);
    }

    WeChatChlUser _currentWeChatChlUser();

    List<WeChatChlUser> _current(Consumer<List<WeChatChlUser>> consumer);

    <R> R _function(Function<MamsSession, R> function);
}