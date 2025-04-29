package com.asialjim.microapplet.mams.channel.wechat.official.infrastructure.adaptor.qrcode;

import com.asialjim.microapplet.mams.channel.wechat.official.cors.event.WeChatOfficialScanEvent;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Objects;

/**
 * 二维码扫码事件处理上下文
 *
 * @author <a href="mailto:asialjim@hotmail.com">Asial Jim</a>
 * @version 1.0
 * @since 2025/4/29, &nbsp;&nbsp; <em>version:1.0</em>
 */
@Component
@AllArgsConstructor
public class SceneQrCodeHandlerContext {
    private final Map<String, SceneQrCodeHandler> qrCodeHandlerMap;

    public ObjectNode handle(WeChatOfficialScanEvent event) {
        String name = SceneQrCodeHandler.beanName(event);
        SceneQrCodeHandler handler = qrCodeHandlerMap.get(name);
        // 没有该二维码业务类型
        if (Objects.isNull(handler))
            return null;
        return handler.execute(event);
    }
}