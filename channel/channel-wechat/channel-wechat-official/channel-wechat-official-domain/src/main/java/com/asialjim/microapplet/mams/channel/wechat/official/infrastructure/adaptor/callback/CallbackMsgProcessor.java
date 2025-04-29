package com.asialjim.microapplet.mams.channel.wechat.official.infrastructure.adaptor.callback;

import com.asialjim.microapplet.mams.channel.wechat.official.domain.WeChatOfficialCallbackMsg;
import com.fasterxml.jackson.databind.node.ObjectNode;

/**
 * 微信公众号消息/事件回调处理器
 *
 * @author <a href="mailto:asialjim@hotmail.com">Asial Jim</a>
 * @version 1.0
 * @since 2025/4/29, &nbsp;&nbsp; <em>version:1.0</em>
 */
public interface CallbackMsgProcessor {

    static String nameFormat(String event, String type){
        return "inner" + event + type + "WxOfficialCallMsgProcessor";
    }

    static String beanName(WeChatOfficialCallbackMsg msg) {
        String msgType = msg.getMsgType().getCode();
        String eventType = msg.getEventType().getCode();
        return nameFormat(eventType, msgType);
    }

    /**
     * 处理消息/事件
     *
     * @param callBackMsg {@link WeChatOfficialCallbackMsg callBackMsg}
     * @return {@link ObjectNode }
     * @since 2025/4/29
     */
    ObjectNode process(WeChatOfficialCallbackMsg callBackMsg);
}