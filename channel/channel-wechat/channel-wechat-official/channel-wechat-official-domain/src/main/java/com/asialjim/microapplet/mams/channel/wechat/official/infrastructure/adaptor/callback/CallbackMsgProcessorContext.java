package com.asialjim.microapplet.mams.channel.wechat.official.infrastructure.adaptor.callback;

import com.asialjim.microapplet.mams.channel.wechat.official.domain.WeChatOfficialCallbackMsg;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Objects;

/**
 * 微信公众号消息/事件处理器路由组件
 *
 * @author <a href="mailto:asialjim@hotmail.com">Asial Jim</a>
 * @version 1.0
 * @since 2025/4/29, &nbsp;&nbsp; <em>version:1.0</em>
 */
@Component
@AllArgsConstructor
public class CallbackMsgProcessorContext {
    private final Map<String, CallbackMsgProcessor> processorMap;

    public ObjectNode process(WeChatOfficialCallbackMsg callBackMsg) {
        String name = CallbackMsgProcessor.beanName(callBackMsg);
        CallbackMsgProcessor processor = processorMap.get(name);
        if (Objects.isNull(processor))
            return null;
        return processor.process(callBackMsg);
    }
}