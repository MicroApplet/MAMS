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

import com.asialjim.microapplet.common.concurrent.ConcurrentRunner;
import com.asialjim.microapplet.mams.channel.wechat.WeChatOfficialCons;
import com.asialjim.microapplet.mams.channel.wechat.official.domain.WeChatOfficialCallbackAISession;
import com.asialjim.microapplet.mams.channel.wechat.official.domain.WeChatOfficialCallbackMsg;
import com.asialjim.microapplet.mams.channel.wechat.official.infrastructure.adaptor.node.EmptyObjectNode;
import com.asialjim.microapplet.mams.channel.wechat.official.infrastructure.remoting.dify.AsialJimDifyRemoting;
import com.asialjim.microapplet.mams.channel.wechat.official.infrastructure.remoting.dify.meta.Message;
import com.asialjim.microapplet.mams.channel.wechat.official.infrastructure.remoting.dify.meta.MessageRes;
import com.asialjim.microapplet.mams.channel.wechat.official.infrastructure.repository.WeChatOfficialCallbackAISessionManager;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.StringJoiner;

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
    @Resource
    private AsialJimDifyRemoting asialJimDifyRemoting;

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
        log.info("当前AI会话：{}", session);

        String content = callBackMsg.textNodeValue(WeChatOfficialCons.XmlMsgTag.content);

        Message msg = new Message();
        msg.setUser(openid);
        msg.setQuery(content);
        msg.setConversation_id(session.getSessionId());
        msg.setResponse_mode("blocking");
        msg.withInput("a", "b");
        MessageRes msgRes = asialJimDifyRemoting.chat(msg);
        List<String> msgList = MessageRes.msg(msgRes);
        StringJoiner sj = new StringJoiner("\r\n");
        msgList.forEach(sj::add);

        ObjectNode res = JsonNodeFactory.instance.objectNode();
        res.put(WeChatOfficialCons.XmlMsgTag.content, sj.toString());
        res.put(WeChatOfficialCons.XmlMsgTag.msgType, WeChatOfficialCons.XmlMsgType.Text.getCode());
        return res;
    }

    public ObjectNode openAiSession(WeChatOfficialCallbackMsg callBackMsg) {
        ConcurrentRunner.runAllTaskAsync(() -> ai(callBackMsg));
        return EmptyObjectNode.instance;
    }

    private void ai(WeChatOfficialCallbackMsg callBackMsg) {
        WeChatOfficialCallbackAISession session = new WeChatOfficialCallbackAISession();
        String openid = callBackMsg.fromUserName().asText();
        Message msg = new Message();
        msg.setUser(openid);
        msg.setQuery("你好");
        msg.setResponse_mode("blocking");
        msg.withInput("a", "b");
        MessageRes msgRes = asialJimDifyRemoting.chat(msg);
        List<String> msgList = MessageRes.msg(msgRes);
        StringJoiner sj = new StringJoiner("\r\n");
        msgList.forEach(sj::add);

        String sessionId = MessageRes.conversation(msgRes);

        session.setSessionId(sessionId);
        session.setOpenid(callBackMsg.fromUserName().asText());
        session.setCreateTime(callBackMsg.createTime().asLong());
        this.wechatOfficialCallbackAISessionManager.addSession(session);

        log.info("模拟发送客服消息：{}", sj);
    }
}