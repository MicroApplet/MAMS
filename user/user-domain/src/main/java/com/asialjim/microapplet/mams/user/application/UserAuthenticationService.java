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

package com.asialjim.microapplet.mams.user.application;

import com.asialjim.microapplet.mams.channel.base.ChlType;
import com.asialjim.microapplet.mams.channel.base.ChlTypeResCode;
import com.asialjim.microapplet.mams.user.command.UserRegCommand;
import com.asialjim.microapplet.mams.user.domain.agg.UserAgg;
import com.asialjim.microapplet.mams.user.domain.strategy.BaseUserAuthenticateStrategy;
import com.asialjim.microapplet.mams.user.pojo.UserMain;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 用户认证服务
 *
 * @author <a href="mailto:asialjim@hotmail.com">Asial Jim</a>
 * @version 1.0
 * @since 2025/4/10, &nbsp;&nbsp; <em>version:1.0</em>
 */
@Component
public class UserAuthenticationService {
    private final Map<ChlType, BaseUserAuthenticateStrategy> strategyMap;

    public UserAuthenticationService(List<BaseUserAuthenticateStrategy> strategies) {
        this.strategyMap = new HashMap<>();
        if (CollectionUtils.isNotEmpty(strategies)) {
            for (BaseUserAuthenticateStrategy strategy : strategies) {
                ChlType chlType = strategy.supportType();
                BaseUserAuthenticateStrategy exist = this.strategyMap.get(chlType);
                if (Objects.nonNull(exist))
                    throw new IllegalStateException("渠道：" + chlType + "存在多个用户认证策略，请合并该渠道策略");
                this.strategyMap.put(chlType, strategy);
            }
        }
    }

    /**
     * 用户注册
     *
     * @param command {@link UserRegCommand command}
     * @return {@link UserMain }
     * @since 2025/4/10
     */
    public UserMain registration(UserRegCommand command) {
        BaseUserAuthenticateStrategy strategy = strategyMap.get(command.regChlType());
        if (Objects.isNull(strategy))
            ChlTypeResCode.ChlTypeNotSupport.throwBiz();

        UserAgg userAgg = command.getUserAgg();
        strategy.registration(command);
        return userAgg.userMain();
    }
}