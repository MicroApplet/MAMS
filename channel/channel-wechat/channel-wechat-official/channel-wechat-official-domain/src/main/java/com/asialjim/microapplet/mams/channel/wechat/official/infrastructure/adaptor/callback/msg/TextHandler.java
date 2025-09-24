package com.asialjim.microapplet.mams.channel.wechat.official.infrastructure.adaptor.callback.msg;

import com.asialjim.microapplet.mams.channel.wechat.WeChatOfficialCons;
import com.asialjim.microapplet.mams.channel.wechat.official.domain.WeChatOfficialCallbackMsg;
import com.asialjim.microapplet.mams.channel.wechat.official.infrastructure.adaptor.callback.CallMsgHandler;
import com.asialjim.microapplet.mams.channel.wechat.official.infrastructure.adaptor.callback.CallbackMsgProcessor;
import com.asialjim.microapplet.mams.channel.wechat.official.infrastructure.adaptor.callback.ai.CallbackMsgAIContext;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.commons.lang3.StringUtils;

import jakarta.annotation.Resource;

/**
 * 文本消息处理器
 *
 * @author <a href="mailto:asialjim@hotmail.com">Asial Jim</a>
 * @version 1.0
 * @since 2025/4/29, &nbsp;&nbsp; <em>version:1.0</em>
 */
@CallMsgHandler(msgType = WeChatOfficialCons.XmlMsgType.Text)
public class TextHandler implements CallbackMsgProcessor {
    @Resource
    private CallbackMsgAIContext callbackMsgAIContext;

    @Override
    public ObjectNode process(WeChatOfficialCallbackMsg callBackMsg) {
        // 消息内容
        String content = callBackMsg.textNodeValue(WeChatOfficialCons.XmlMsgTag.content);
        if (StringUtils.equalsAnyIgnoreCase(content, "AI"))
            return callbackMsgAIContext.openAiSession(callBackMsg);

        // TODO 对接 AI 系统
        return null;
    }
}