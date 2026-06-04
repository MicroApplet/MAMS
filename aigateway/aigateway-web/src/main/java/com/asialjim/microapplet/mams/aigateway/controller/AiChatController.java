package com.asialjim.microapplet.mams.aigateway.controller;

import com.asialjim.microapplet.mams.aigateway.channel.ChannelExecutorRouter;
import com.asialjim.microapplet.mams.aigateway.intent.IntentRecognitionEngine;
import com.asialjim.microapplet.mams.aigateway.intent.IntentResult;
import com.asialjim.microapplet.mams.aigateway.router.Channel;
import com.asialjim.microapplet.mams.aigateway.router.Router;
import com.asialjim.microapplet.mams.aigateway.session.Session;
import com.asialjim.microapplet.mams.aigateway.session.SessionManager;
import com.asialjim.microapplet.mams.aigateway.sse.SseEmitterHelper;
import jakarta.annotation.PreDestroy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@RestController
@RequestMapping("/ai")
public class AiChatController {
    private static final Logger log = LoggerFactory.getLogger(AiChatController.class);
    private final SessionManager sessionManager;
    private final IntentRecognitionEngine intentEngine;
    private final Router router;
    private final ChannelExecutorRouter channelRouter;
    private final ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor();

    public AiChatController(SessionManager sm, IntentRecognitionEngine ie, Router r, ChannelExecutorRouter cr) {
        this.sessionManager = sm; this.intentEngine = ie; this.router = r; this.channelRouter = cr;
    }

    @PostMapping(value = "/chat", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter chat(@RequestBody ChatRequest req, @RequestHeader("X-User-Id") String userId,
                           @RequestHeader(value="X-User-Role",required=false) String role,
                           @RequestHeader(value="X-Platform",required=false) String platform) {
        String sid = req.getSessionId() != null ? req.getSessionId() : UUID.randomUUID().toString();
        Session session = sessionManager.getOrCreate(sid, userId, platform != null ? platform : "unknown");
        if (role != null) session.setRoleBit(role);
        log.info("AI 对话: session={}, userId={}, message={}", sid, userId, req.getMessage());
        SseEmitter emitter = new SseEmitter(300_000L);
        executor.submit(() -> {
            try {
                SseEmitterHelper.thinking(emitter, "正在理解您的请求…");
                IntentResult intent = intentEngine.recognize(req.getMessage(), session);
                if ("unknown".equals(intent.getIntent())) {
                    SseEmitterHelper.text(emitter, "抱歉，没有理解您的意思。");
                    SseEmitterHelper.done(emitter);
                    return;
                }
                Channel ch = router.route(intent);
                log.info("意图 {} 路由到通道 {}", intent.getIntent(), ch);
                SseEmitterHelper.thinking(emitter, "已识别意图: "+intent.getIntent()+"，正在通过 "+ch+" 通道处理");
                channelRouter.getExecutor(ch).execute(emitter, intent, session);
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
