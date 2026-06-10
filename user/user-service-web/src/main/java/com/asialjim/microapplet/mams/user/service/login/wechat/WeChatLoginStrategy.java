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

package com.asialjim.microapplet.mams.user.service.login.wechat;


import com.asialjim.microapplet.common.security.MamsSession;
import com.asialjim.microapplet.mams.app.cons.ChannelAppType;
import com.asialjim.microapplet.mams.app.cons.ChannelType;
import com.asialjim.microapplet.mams.app.context.ChlRs;
import com.asialjim.microapplet.mams.app.vo.ChlAppVo;
import com.asialjim.microapplet.mams.user.service.login.ChlLoginStrategy;
import com.asialjim.microapplet.mams.user.vo.LoginReq;
import jakarta.annotation.Resource;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 微信登录策略
 *
 * @author <a href="mailto:asialjim@hotmail.com">Asial Jim</a>
 * @version 1.0
 * @since 2025年9月23日, &nbsp;&nbsp; <em>version:1.0</em>
 */
@Component
@AllArgsConstructor
public class WeChatLoginStrategy implements ChlLoginStrategy {
    private final List<WeChatTypeLoginStrategy> weChatTypeLoginStrategies;

    /**
     * 支持
     *
     * @param type 类型
     * @return boolean
     */
    @Override
    public boolean support(ChannelType type) {
        return ChannelType.WeChat.equals(type);
    }

    /**
     * 登录
     *
     * @param chlApp 渠道应用信息
     * @param req    登录参数
     * @return {@link MamsSession}
     */
    @Override
    public MamsSession login(ChlAppVo chlApp, LoginReq req){
        ChannelAppType channelAppType = ChlAppVo.chlAppType(chlApp);

        return weChatTypeLoginStrategies
                .stream()
                .filter(item -> item.supportChlAppType(channelAppType))
                .findFirst()
                .orElseThrow(ChlRs.NoSuchChlApp::ex)
                .login(chlApp, req);
    }
}