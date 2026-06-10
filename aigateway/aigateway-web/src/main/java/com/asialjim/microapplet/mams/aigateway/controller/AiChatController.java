/*
 *    Copyright 2014-2025 <a href="mailto:asialjim@qq.com">Asial Jim</a>
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package com.asialjim.microapplet.mams.aigateway.controller;

import com.asialjim.microapplet.common.security.MamsSession;
import com.asialjim.microapplet.common.security.MamsSessionContextHolder;
import com.asialjim.microapplet.mams.aigateway.channel.ChannelExecutorRouter;
import com.asialjim.microapplet.mams.aigateway.intent.IntentRecognitionEngine;
import com.asialjim.microapplet.mams.aigateway.intent.IntentResult;
import com.asialjim.microapplet.mams.aigateway.router.Channel;
import com.asialjim.microapplet.mams.aigateway.router.Router;
import com.asialjim.microapplet.mams.aigateway.sse.SseEmitterHelper;
import jakarta.annotation.PreDestroy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@RestController
@RequestMapping("/ai")
public class AiChatController {
    private static final Logger log = LoggerFactory.getLogger(AiChatController.class);
    private final IntentRecognitionEngine intentEngine;
    private final Router router;
    private final ChannelExecutorRouter channelRouter;
    private final ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor();

    public AiChatController(IntentRecognitionEngine ie, Router r, ChannelExecutorRouter cr) {
        this.intentEngine = ie; this.router = r; this.channelRouter = cr;
    }

    @PostMapping(value = "/chat", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter chat(@RequestBody ChatRequest req) {

        MamsSession mamsSession = MamsSessionContextHolder.current()
                .orElseThrow(() -> new IllegalStateException("未找到用户会话，请先登录"));
        log.info("AI 对话: userId={}, message={}", mamsSession.getUserid(), req.getMessage());

        SseEmitter emitter = new SseEmitter(300_000L);
        executor.submit(() -> {
            try {
                SseEmitterHelper.thinking(emitter, "正在理解您的请求…");
                IntentResult intent = intentEngine.recognize(req.getMessage());
                if ("unknown".equals(intent.getIntent())) {
                    SseEmitterHelper.text(emitter, "抱歉，没有理解您的意思。");
                    SseEmitterHelper.done(emitter);
                    return;
                }
                Channel ch = router.route(intent);
                log.info("意图 {} 路由到通道 {}", intent.getIntent(), ch);
                SseEmitterHelper.thinking(emitter, "已识别意图: "+intent.getIntent()+"，正在通过 "+ch+" 通道处理");
                channelRouter.getExecutor(ch).execute(emitter, intent);
            } catch (Exception e) { log.error("处理异常", e); SseEmitterHelper.error(emitter, "INTERNAL_ERROR", "服务内部错误"); }
        });
        return emitter;
    }

    @PreDestroy public void shutdown() { executor.close(); }

    public static class ChatRequest {
        private String sessionId; private String message;
        public String getSessionId() { return sessionId; } public void setSessionId(String s) { this.sessionId = s; }
        public String getMessage() { return message; } public void setMessage(String m) { this.message = m; }
    }
}
