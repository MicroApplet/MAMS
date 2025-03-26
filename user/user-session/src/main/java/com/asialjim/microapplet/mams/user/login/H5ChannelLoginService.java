/*
 *  Copyright 2014-2025 <a href="mailto:asialjim@qq.com">Asial Jim</a>
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *   limitations under the License.
 */

package com.asialjim.microapplet.mams.user.login;

import com.asialjim.microapplet.common.utils.PasswordStorage;
import com.asialjim.microapplet.mams.user.cons.Channel;
import com.asialjim.microapplet.mams.user.model.User;
import com.asialjim.microapplet.mams.user.parameter.LoginParameter;
import com.asialjim.microapplet.mams.user.repository.UserRepository;
import com.asialjim.microapplet.mams.user.res.UserResCode;
import com.asialjim.microapplet.mams.user.session.SessionUser;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * H5 渠道用户登录服务
 *
 * @author <a href="mailto:asialjim@hotmail.com">Asial Jim</a>
 * @version 1.0
 * @since 2025/3/11, &nbsp;&nbsp; <em>version:1.0</em>
 */
@Component
public class H5ChannelLoginService implements ChannelLoginService {
    @Resource
    private UserRepository userRepository;

    @Override
    public String chlCode() {
        return Channel.H5.getCode();
    }

    @Override
    public SessionUser login(LoginParameter loginParameter) {
        String appid = loginParameter.getAppid();
        String username = loginParameter.getUsername();
        String password = loginParameter.getUserCode();

        User user = this.userRepository.queryByAppidUsername(appid, username);
        if (Objects.isNull(user))
            UserResCode.UserNameOrPasswordErr.throwBiz();

        boolean passwordCheck = PasswordStorage.verifyPassword(password, user.getPassword());
        if (!passwordCheck)
            UserResCode.UserNameOrPasswordErr.throwBiz();

        SessionUser sessionUser = new SessionUser();
        sessionUser.setUserid(user.getId());
        sessionUser.setNickname(user.getNickname());
        sessionUser.setUsername(user.getUsername());
        return sessionUser;
    }
}