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

package com.asialjim.microapplet.mams.channel.wechat.official.infrastructure.adaptor.callback.ai;

import com.asialjim.microapplet.mams.channel.wechat.official.domain.WeChatOfficialCallbackAISession;
import com.asialjim.microapplet.mams.channel.wechat.official.domain.WeChatOfficialCallbackMsg;
import com.asialjim.microapplet.mams.channel.wechat.official.infrastructure.adaptor.node.EmptyObjectNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * 微信公众号回调消息AI路由组件
 *
 * @author <a href="mailto:asialjim@hotmail.com">Asial Jim</a>
 * @version 1.0
 * @since 2025/5/7, &nbsp;&nbsp; <em>version:1.0</em>
 */
@Component
public class CallbackMsgAIContext {

    /**
     * 将公众号消息/事件路由到AI系统
	 * @param session {@link WeChatOfficialCallbackAISession session}
	 * @param callBackMsg {@link WeChatOfficialCallbackMsg callBackMsg}
     * @return {@link ObjectNode }
     * @since 2025/5/7
     */
    public ObjectNode aiRoute(WeChatOfficialCallbackAISession session, WeChatOfficialCallbackMsg callBackMsg){
        // 没有AI会话
        if (Objects.isNull(session) || StringUtils.isBlank(session.getSessionId()))
            return null;

        // TODO 对接AI系统
        return null;
    }
}