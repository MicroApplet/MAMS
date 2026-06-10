package com.asialjim.microapplet.mams.aigateway.channel;

import com.asialjim.microapplet.mams.aigateway.intent.IntentResult;
import com.asialjim.microapplet.mams.aigateway.sse.SseEmitterHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Component
public class DirectMcpExecutor implements ChannelExecutor {
    private static final Logger log = LoggerFactory.getLogger(DirectMcpExecutor.class);
    @Override
    public void execute(SseEmitter emitter, IntentResult intent) {
        log.info("直调 MCP Tool: intent={}", intent.getIntent());
        SseEmitterHelper.thinking(emitter, "正在直调工具: " + intent.getIntent());
        SseEmitterHelper.toolCall(emitter, intent.getIntent(), intent.getParams());
        SseEmitterHelper.toolResult(emitter, intent.getIntent(), java.util.Map.of("status","ok","message","MCP 执行成功（模拟）"));
        SseEmitterHelper.text(emitter, "已通过 MCP 直调完成 " + intent.getIntent());
        SseEmitterHelper.done(emitter);
    }
}
