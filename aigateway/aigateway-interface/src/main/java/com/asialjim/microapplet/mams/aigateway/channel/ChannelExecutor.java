package com.asialjim.microapplet.mams.aigateway.channel;

import com.asialjim.microapplet.mams.aigateway.intent.IntentResult;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

public interface ChannelExecutor {
    void execute(SseEmitter emitter, IntentResult intent);
}
