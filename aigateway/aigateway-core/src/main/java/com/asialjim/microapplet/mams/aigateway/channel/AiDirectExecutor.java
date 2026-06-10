package com.asialjim.microapplet.mams.aigateway.channel;

import com.asialjim.microapplet.mams.aigateway.intent.IntentResult;
import com.asialjim.microapplet.mams.aigateway.sse.SseEmitterHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Component
public class AiDirectExecutor implements ChannelExecutor {
    private static final Logger log = LoggerFactory.getLogger(AiDirectExecutor.class);
    @Override
    public void execute(SseEmitter emitter, IntentResult intent) {
        log.info("直连 AI 引擎: intent={}", intent.getIntent());
        SseEmitterHelper.thinking(emitter, "正在调用 AI 引擎处理: " + intent.getIntent());
        SseEmitterHelper.text(emitter, "AI 引擎处理结果（模拟）:\n意图: " + intent.getIntent() + "\n参数: " + intent.getParams());
        SseEmitterHelper.done(emitter);
    }
}
