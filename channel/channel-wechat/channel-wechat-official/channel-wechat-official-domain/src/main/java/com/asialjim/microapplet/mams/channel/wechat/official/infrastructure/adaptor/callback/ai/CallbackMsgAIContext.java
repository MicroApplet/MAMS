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

import com.asialjim.microapplet.mams.channel.wechat.WeChatOfficialCons;
import com.asialjim.microapplet.mams.channel.wechat.official.domain.WeChatOfficialCallbackAISession;
import com.asialjim.microapplet.mams.channel.wechat.official.domain.WeChatOfficialCallbackMsg;
import com.asialjim.microapplet.mams.channel.wechat.official.infrastructure.adaptor.node.EmptyObjectNode;
import com.asialjim.microapplet.mams.channel.wechat.official.infrastructure.repository.WeChatOfficialCallbackAISessionManager;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * 微信公众号回调消息AI路由组件
 *
 * @author <a href="mailto:asialjim@hotmail.com">Asial Jim</a>
 * @version 1.0
 * @since 2025/5/7, &nbsp;&nbsp; <em>version:1.0</em>
 */
@Slf4j
@Component
public class CallbackMsgAIContext {
    @Resource
    private WeChatOfficialCallbackAISessionManager wechatOfficialCallbackAISessionManager;

    /**
     * 将公众号消息/事件路由到AI系统
     *
     * @param callBackMsg {@link WeChatOfficialCallbackMsg callBackMsg}
     * @return {@link ObjectNode }
     * @since 2025/5/7
     */
    public ObjectNode aiRoute(WeChatOfficialCallbackMsg callBackMsg) {
        String openid = callBackMsg.fromUserName().asText();
        WeChatOfficialCallbackAISession session = this.wechatOfficialCallbackAISessionManager.sessionOfUser(openid);
        // 非AI 模式
        if (WeChatOfficialCallbackAISession.emptySession(session))
            return null;

        // 会话续期
        session = this.wechatOfficialCallbackAISessionManager.addSession(session);
        log.info("当前AI会话：{}",session);


        // TODO 对接AI系统
        return null;
    }

    public ObjectNode openAiSession(WeChatOfficialCallbackMsg callBackMsg) {
        WeChatOfficialCallbackAISession session = new WeChatOfficialCallbackAISession();
        String sessionId = callBackMsg.fromUserName().asText() + callBackMsg.msgId().asText();
        session.setSessionId(sessionId);
        session.setOpenid(callBackMsg.fromUserName().asText());
        session.setCreateTime(callBackMsg.createTime().asLong());
        this.wechatOfficialCallbackAISessionManager.addSession(session);
        ObjectNode res = JsonNodeFactory.instance.objectNode();
        res.put(WeChatOfficialCons.XmlMsgTag.content, "您已进入AI模式");
        aiRoute(callBackMsg);
        return res;
    }
}