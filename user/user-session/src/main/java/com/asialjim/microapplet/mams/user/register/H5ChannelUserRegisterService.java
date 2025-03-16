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

package com.asialjim.microapplet.mams.user.register;

import com.asialjim.microapplet.common.context.Res;
import com.asialjim.microapplet.common.utils.PasswordStorage;
import com.asialjim.microapplet.mams.user.model.User;
import com.asialjim.microapplet.mams.user.parameter.H5RegisterUserParameter;
import com.asialjim.microapplet.mams.user.parameter.RegisterUserParameter;
import com.asialjim.microapplet.mams.user.repository.UserRepository;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * H5平台用户注册
 *
 * @author <a href="mailto:asialjim@hotmail.com">Asial Jim</a>
 * @version 1.0
 * @since 2025/3/13, &nbsp;&nbsp; <em>version:1.0</em>
 */
@Validated
@Component
public class H5ChannelUserRegisterService implements ChannelUserRegisterService{
    @Resource private UserRepository userRepository;

    @Override
    public String chlCode() {
        return "H5";
    }

    @Override
    public User register(@NotNull(message = "用户注册参数为空") @Valid RegisterUserParameter parameter) {
        if (Objects.isNull(parameter) || !(parameter instanceof H5RegisterUserParameter))
            Res.KeyParameterTypeErr.throwBiz();

        //noinspection DataFlowIssue
        H5RegisterUserParameter userParameter = (H5RegisterUserParameter) parameter;
        User user = new User();
        user.setId("");
        user.setAppid(userParameter.getAppid());
        user.setNickname(userParameter.getNickname());
        user.setUsername(userParameter.getUsername());
        user.setPassword(PasswordStorage.createHash(userParameter.getPassword()));
        user = this.userRepository.register(user);
        return  user;
    }
}