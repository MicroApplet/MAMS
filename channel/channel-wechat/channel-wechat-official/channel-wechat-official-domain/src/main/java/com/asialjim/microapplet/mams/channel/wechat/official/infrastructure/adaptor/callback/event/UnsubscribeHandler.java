package com.asialjim.microapplet.mams.channel.wechat.official.infrastructure.adaptor.callback.event;

import com.asialjim.microapplet.common.event.EventUtil;
import com.asialjim.microapplet.mams.channel.wechat.WeChatOfficialCons;
import com.asialjim.microapplet.mams.channel.wechat.official.cors.event.WeChatOfficialUnsubscribeEvent;
import com.asialjim.microapplet.mams.channel.wechat.official.domain.WeChatOfficialCallbackMsg;
import com.asialjim.microapplet.mams.channel.wechat.official.infrastructure.adaptor.callback.CallMsgHandler;
import com.asialjim.microapplet.mams.channel.wechat.official.infrastructure.adaptor.callback.CallbackMsgProcessor;
import com.fasterxml.jackson.databind.node.ObjectNode;

/**
 * 关注事件处理器
 *
 * @author <a href="mailto:asialjim@hotmail.com">Asial Jim</a>
 * @version 1.0
 * @since 2025/4/29, &nbsp;&nbsp; <em>version:1.0</em>
 */
@CallMsgHandler(
        msgType = WeChatOfficialCons.XmlMsgType.Event,
        eventType = WeChatOfficialCons.XmlEventType.Unsubscribe
)
public class UnsubscribeHandler implements CallbackMsgProcessor {

    @Override
    public ObjectNode process(WeChatOfficialCallbackMsg callBackMsg) {
        final String openid = callBackMsg.fromUserName().asText();
        final String subjectId = callBackMsg.toUserName().asText();
        final Long subTime = callBackMsg.createTime().longValue();
        // 取关事件
        final WeChatOfficialUnsubscribeEvent subscribeEvent = new WeChatOfficialUnsubscribeEvent(openid, subjectId, subTime);
        EventUtil.push(subscribeEvent);
        return null;
    }
}