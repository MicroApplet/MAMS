/*
 *    Copyright 2014-2025 <a href="mailto:asialjim@qq.com">Asial Jim</a>
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package com.asialjim.microapplet.mams.user.service.registrar;

import com.asialjim.microapplet.common.context.Result;
import com.asialjim.microapplet.mams.app.cons.ChannelAppType;
import com.asialjim.microapplet.mams.app.cons.ChannelType;
import com.asialjim.microapplet.mams.app.context.ChlRs;
import com.asialjim.microapplet.mams.user.service.registrar.pc.CMSPCUserRegistrarStrategy;
import com.asialjim.microapplet.mams.user.vo.ChlUserVo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

/**
 * 用户注册服务
 *
 * @author <a href="mailto:asialjim@hotmail.com">Asial Jim</a>
 * @version 1.0
 * @since 2025/9/29, &nbsp;&nbsp; <em>version:1.0</em>
 */
@Component
@RequiredArgsConstructor
public class UserRegistrarService {
    private final List<ChlUserRegistrarStrategy> registrarStrategies;
    private final CMSPCUserRegistrarStrategy cmspcUserRegistrarStrategy;

    public Result<Void> rootExist() {
        return cmspcUserRegistrarStrategy.rootExist();
    }

    public ChlUserVo register(ChlUserVo user) {
        String chlType = user.getChlType();
        String chlAppType = user.getChlAppType();
        ChannelType channelType = ChannelType.codeOf(chlType);
        ChannelAppType channelAppType = ChannelAppType.codeOf(chlAppType);
        return strategy(channelType, channelAppType).register(user);
    }

    private ChlUserRegistrarStrategy strategy(ChannelType type, ChannelAppType channelAppType) {
        return this.registrarStrategies.stream()
                .filter(Objects::nonNull)
                .filter(item -> item.registerChannel().equals(type))
                .filter(item -> item.chlAppType().equals(channelAppType))
                .findAny()
                .orElseThrow(ChlRs.RegistrarChlIllegal::ex);
    }

}