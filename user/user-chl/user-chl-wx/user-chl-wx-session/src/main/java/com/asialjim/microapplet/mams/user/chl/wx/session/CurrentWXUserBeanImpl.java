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

import com.asialjim.microapplet.mams.user.chl.wx.WxUserChlRes;
import com.asialjim.microapplet.mams.user.chl.wx.model.WeChatChlUser;
import com.asialjim.microapplet.mams.user.chl.wx.repository.WeChatChlUserRepository;
import com.asialjim.microapplet.mams.user.cons.Channel;
import com.asialjim.microapplet.mams.user.model.User;
import com.asialjim.microapplet.mams.user.res.UserResCode;
import com.asialjim.microapplet.mams.user.session.CurrentUserBean;
import com.asialjim.microapplet.mams.user.session.MamsSession;
import com.asialjim.microapplet.mams.user.session.SessionUser;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * 当前微信渠道用户组件
 *
 * @author <a href="mailto:asialjim@hotmail.com">Asial Jim</a>
 * @version 1.0
 * @since 2025/3/26, &nbsp;&nbsp; <em>version:1.0</em>
 */
@Setter
@Component
public class CurrentWXUserBeanImpl implements CurrentWXUserBean, ApplicationContextAware {
    public CurrentWXUserBeanImpl() {
        CurrentWXUserBean.instance(this);
    }

    private ApplicationContext applicationContext;
    @Resource
    private WeChatChlUserRepository weChatChlUserRepository;

    @Override
    public WeChatChlUser _currentWeChatChlUser() {
        SessionUser sessionUser = sessionUser();
        String chlCode = sessionUser.getChlCode();
        if (!StringUtils.equals(chlCode, Channel.WeChat.getCode()))
            WxUserChlRes.CurrentUserLoginChannelErr.throwBiz();
        return this.weChatChlUserRepository.queryByOpenid(sessionUser.getChlOpenid());
    }

    @Override
    public List<WeChatChlUser> _current(Consumer<List<WeChatChlUser>> consumer) {
        User user = CurrentUserBean.current(null);
        List<WeChatChlUser> wxChlUser = weChatChlUserRepository.queryByUserid(user.getId());
        if (Objects.nonNull(consumer))
            consumer.accept(wxChlUser);
        return wxChlUser;
    }

    @Override
    public <R> R _function(Function<MamsSession, R> function) {
        try {
            SessionUser sessionUser = sessionUser();
            return function.apply(sessionUser);
        } catch (Throwable t) {
            return null;
        }
    }


    private SessionUser sessionUser() {
        try {
            return applicationContext.getBean(SessionUser.sessionUser, SessionUser.class);
        } catch (Throwable t) {
            throw UserResCode.UserNotLogin.bizException();
        }
    }
}