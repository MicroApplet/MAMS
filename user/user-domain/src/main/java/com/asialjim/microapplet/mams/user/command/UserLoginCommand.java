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

package com.asialjim.microapplet.mams.user.command;

import com.asialjim.microapplet.common.application.App;
import com.asialjim.microapplet.mams.channel.base.ChlAppType;
import com.asialjim.microapplet.mams.channel.base.ChlType;
import com.asialjim.microapplet.mams.channel.base.ChlTypeResCode;
import com.asialjim.microapplet.mams.user.domain.agg.SessionUser;
import com.asialjim.microapplet.mams.user.domain.agg.UserAggRoot;
import com.asialjim.microapplet.mams.user.vo.UserRegOrLoginReq;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;

/**
 * 用户登录命令
 *
 * @author <a href="mailto:asialjim@hotmail.com">Asial Jim</a>
 * @version 1.0
 * @since 2025/4/10, &nbsp;&nbsp; <em>version:1.0</em>
 */
@Data
@Accessors(chain = true)
public class UserLoginCommand implements Serializable {
    private static final long serialVersionUID = -3853932921226593364L;

    private UserAggRoot userAgg;
    private UserRegOrLoginReq req;

    public ChlType regChlType() {
        return Optional.ofNullable(req)
                .map(UserRegOrLoginReq::getChlType)
                .orElseThrow(ChlTypeResCode.ChlTypeNotProvide::bizException);
    }

    public ChlAppType regChlAppType() {
        return Optional.ofNullable(req)
                .map(UserRegOrLoginReq::getChlAppType)
                .orElseThrow(ChlTypeResCode.ChlTypeNotProvide::bizException);
    }

    public UserAggRoot getUserAgg() {
        if (Objects.nonNull(userAgg))
            return userAgg;
        App.beanAndThen(UserAggRoot.class, this::setUserAgg);
        return userAgg;
    }

    public UserAggRoot getUserAggWithUserid(String userid) {
        if (Objects.isNull(userAgg)){
            App.beanAndThen(UserAggRoot.class, this::setUserAgg);
        }

        SessionUser sessionUser = userAgg.getSessionUser();
        sessionUser.setUserid(userid);
        return userAgg;
    }


    public static UserLoginCommand commandOf(UserRegOrLoginReq req) {
        return new UserLoginCommand().setReq(req);
    }
}