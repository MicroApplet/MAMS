package com.asialjim.microapplet.mams.aigateway.sse;

import com.asialjim.microapplet.commons.sse.SseEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import java.util.Map;

/**
 * SSE 发射器工具类 — 使用 commons-sse 规范数据结构
 */
public class SseEmitterHelper {
    private static final Logger log = LoggerFactory.getLogger(SseEmitterHelper.class);
    private static final ObjectMapper mapper = new ObjectMapper();
    private SseEmitterHelper() {}

    public static void send(SseEmitter emitter, SseEvent event) {
        try {
            emitter.send(SseEmitter.event()
                    .name(event.getType().getEventName())
                    .data(mapper.writeValueAsString(event.getPayload()), MediaType.APPLICATION_JSON));
        } catch (Exception e) {
            log.warn("SSE 发送失败: event={}", event.getType(), e);
            completeWithError(emitter, e);
        }
    }

    /** 便捷方法：thinking */
    public static void thinking(SseEmitter emitter, String text) {
        send(emitter, SseEvent.thinking(text));
    }

    /** 便捷方法：text */
    public static void text(SseEmitter emitter, String text) {
        send(emitter, SseEvent.text(text));
    }

    /** 便捷方法：tool_call */
    public static void toolCall(SseEmitter emitter, String tool, Map<String, Object> params) {
        send(emitter, SseEvent.toolCall(tool, params));
    }

    /** 便捷方法：tool_result */
    public static void toolResult(SseEmitter emitter, String tool, Object result) {
        send(emitter, SseEvent.toolResult(tool, result));
    }

    /** 便捷方法：data */
    public static void data(SseEmitter emitter, Object schema, java.util.List<java.util.Map<String, Object>> dataList) {
        send(emitter, SseEvent.data(schema, dataList));
    }

    /** 便捷方法：ui */
    public static void ui(SseEmitter emitter, com.asialjim.microapplet.commons.sse.payload.UiPayload payload) {
        send(emitter, SseEvent.ui(payload));
    }

    /** 便捷方法：error */
    public static void error(SseEmitter emitter, String code, String message) {
        send(emitter, SseEvent.error(code, message));
    }

    /** 便捷方法：require_input */
    public static void requireInput(SseEmitter emitter, String question, String hint) {
        send(emitter, SseEvent.requireInput(question, hint));
    }

    /** 发送 done 事件并完成 */
    public static void done(SseEmitter emitter) {
        try {
            emitter.send(SseEmitter.event().name("done").data("[DONE]"));
            emitter.complete();
        } catch (Exception e) {
            log.warn("done 发送失败", e);
            completeWithError(emitter, e);
        }
    }

    private static void completeWithError(SseEmitter emitter, Exception e) {
        try { emitter.completeWithError(e); } catch (Exception ignored) {}
    }
}
