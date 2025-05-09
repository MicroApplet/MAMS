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
import com.asialjim.microapplet.mams.channel.wechat.infrastructure.remoting.meta.BaseWeChatApiRes;
import com.asialjim.microapplet.mams.channel.wechat.official.domain.WeChatOfficialCallbackAISession;
import com.asialjim.microapplet.mams.channel.wechat.official.domain.WeChatOfficialCallbackMsg;
import com.asialjim.microapplet.mams.channel.wechat.official.infrastructure.adaptor.node.EmptyObjectNode;
import com.asialjim.microapplet.mams.channel.wechat.official.infrastructure.remoting.customer.WeChatPaCustomerMessageRemoting;
import com.asialjim.microapplet.mams.channel.wechat.official.infrastructure.remoting.customer.meta.WeChatCustomerTextMessage;
import com.asialjim.microapplet.mams.channel.wechat.official.infrastructure.remoting.dify.AsialJimDifyRemoting;
import com.asialjim.microapplet.mams.channel.wechat.official.infrastructure.remoting.dify.meta.FunReq;
import com.asialjim.microapplet.mams.channel.wechat.official.infrastructure.remoting.dify.meta.FunRes;
import com.asialjim.microapplet.mams.channel.wechat.official.infrastructure.remoting.dify.meta.Message;
import com.asialjim.microapplet.mams.channel.wechat.official.infrastructure.remoting.dify.meta.MessageRes;
import com.asialjim.microapplet.mams.channel.wechat.official.infrastructure.repository.WeChatOfficialCallbackAISessionManager;
import com.asialjim.microapplet.remote.net.jackson.AbstractJacksonUtil;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RegExUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;
import java.util.UUID;

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
    private WeChatPaCustomerMessageRemoting weChatPaCustomerMessageRemoting;
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

        JsonNode jsonMsg = callBackMsg.getMsg();
        String json = AbstractJacksonUtil.writeValueAsJsonString(jsonMsg);
        Map<String, Object> map = AbstractJacksonUtil.json2map(json, Object.class);

        Message msg = new Message();
        //FunReq msg = new FunReq();
        msg.setInputs(map);
        msg.setUser(openid);
        msg.setResponse_mode("blocking");
        msg.setQuery("用户发送了消息/事件");

        /*
        FunReq msg = new FunReq();
        msg.setUser(openid);
        msg.setInputs(map);
        */

        final WeChatOfficialCallbackAISession finalSession = session;
        ConcurrentRunner.runAllTaskAsync(() -> ai(callBackMsg, msg, finalSession, openid, callBackMsg.toUserName().asText()));
        return EmptyObjectNode.instance;
    }

    public ObjectNode openAiSession(WeChatOfficialCallbackMsg callBackMsg) {
        ConcurrentRunner.runAllTaskAsync(() -> ai(callBackMsg));
        return EmptyObjectNode.instance;
    }

    private void ai(WeChatOfficialCallbackMsg callBackMsg) {
        WeChatOfficialCallbackAISession session = new WeChatOfficialCallbackAISession();
        String subjectId = callBackMsg.toUserName().asText();
        JsonNode jsonMsg = callBackMsg.getMsg();
        String json = AbstractJacksonUtil.writeValueAsJsonString(jsonMsg);
        Map<String, Object> map = AbstractJacksonUtil.json2map(json, Object.class);
        String openid = callBackMsg.fromUserName().asText();
        Message msg = new Message();
        //FunReq msg = new FunReq();
        msg.setInputs(map);
        msg.setUser(openid);
        msg.setResponse_mode("blocking");
        msg.setQuery("用户发送了消息/事件");
        ai(callBackMsg, msg, session, openid, subjectId);
    }

    private void ai(WeChatOfficialCallbackMsg callBackMsg,
//                    FunReq msg,
                    Message msg,
                    WeChatOfficialCallbackAISession session,
                    String openid,
                    String subjectId) {

        msg.setResponse_mode("blocking");
//        FunRes chat = asialJimDifyRemoting.chat(msg);

        MessageRes msgRes = asialJimDifyRemoting.chat(msg);
        List<String> msgList = MessageRes.answer(msgRes);
        StringJoiner sj = new StringJoiner("\r\n");
        msgList.forEach(sj::add);

        //String sessionId = UUID.randomUUID().toString();
        String sessionId = MessageRes.conversation(msgRes);

        session.setSessionId(sessionId);
        session.setOpenid(callBackMsg.fromUserName().asText());
        session.setCreateTime(callBackMsg.createTime().asLong());
        this.wechatOfficialCallbackAISessionManager.addSession(session);
//        String content = FunRes.content(chat);
        // fun.answer();
        String content = sj.toString();
        content = RegExUtils.replacePattern(content, "<think>(?s)(.*?)</think>", StringUtils.EMPTY);
        content = RegExUtils.replacePattern(content, "<details>(?s)(.*?)</details>", StringUtils.EMPTY);
        content = StringUtils.trim(content);

        WeChatCustomerTextMessage message = new WeChatCustomerTextMessage();

        message.setText(content);
        message.setTouser(openid);
        BaseWeChatApiRes baseWeChatApiRes = weChatPaCustomerMessageRemoting.sendCustomerMsg(subjectId, message);
        log.info("发送客服消息：{},结果：{}", content, baseWeChatApiRes);
    }
}