package com.asialjim.microapplet.mams.aigateway.channel;

import com.asialjim.microapplet.mams.aigateway.intent.IntentResult;
import com.asialjim.microapplet.mams.aigateway.session.Session;
import com.asialjim.microapplet.mams.aigateway.sse.SseEmitterHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Component
public class DifyDelegateExecutor implements ChannelExecutor {
    private static final Logger log = LoggerFactory.getLogger(DifyDelegateExecutor.class);
    @Override
    public void execute(SseEmitter emitter, IntentResult intent, Session session) {
        log.info("委托 Dify: intent={}", intent.getIntent());
        SseEmitterHelper.thinking(emitter, "正在委托 Dify 处理: " + intent.getIntent());
        SseEmitterHelper.text(emitter, "Dify 处理结果（模拟）:\n意图: " + intent.getIntent());
        SseEmitterHelper.done(emitter);
    }
}
