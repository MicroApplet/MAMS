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
import com.asialjim.microapplet.mams.user.domain.agg.UserAgg;
import com.asialjim.microapplet.mams.user.vo.UserRegReq;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;

/**
 * 用户注册命令
 *
 * @author <a href="mailto:asialjim@hotmail.com">Asial Jim</a>
 * @version 1.0
 * @since 2025/4/10, &nbsp;&nbsp; <em>version:1.0</em>
 */
@Data
@Accessors(chain = true)
public class UserRegCommand implements Serializable {
    private static final long serialVersionUID = 6280065545023907004L;

    private UserAgg userAgg;
    private UserRegReq req;

    public ChlType regChlType() {
        return Optional.ofNullable(req)
                .map(UserRegReq::getChlType)
                .orElseThrow(ChlTypeResCode.ChlTypeNotProvide::bizException);
    }

    public UserAgg getUserAgg() {
        if (Objects.nonNull(userAgg))
            return userAgg;
        App.beanAndThen(UserAgg.class, this::setUserAgg);
        return userAgg;
    }

    public static UserRegCommand commandOf(UserRegReq req) {
        return new UserRegCommand().setReq(req);
    }

    public ChlAppType regChlAppType() {
        return Optional.ofNullable(req)
                .map(UserRegReq::getChlAppType)
                .orElseThrow(ChlTypeResCode.ChlTypeNotProvide::bizException);
    }
}