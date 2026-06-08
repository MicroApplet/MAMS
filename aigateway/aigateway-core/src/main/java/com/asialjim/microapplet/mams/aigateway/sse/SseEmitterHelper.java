package com.asialjim.microapplet.mams.aigateway.sse;

import com.asialjim.microapplet.commons.sse.SseEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import java.util.Map;

public class SseEmitterHelper {
    private static final Logger log = LoggerFactory.getLogger(SseEmitterHelper.class);
    private static final ObjectMapper mapper = new ObjectMapper();
    private SseEmitterHelper() {}

    public static void send(SseEmitter emitter, String event, Object data) {
        try {
            String json = data instanceof String ? (String) data : mapper.writeValueAsString(data);
            emitter.send(SseEmitter.event().name(event).data(json, MediaType.APPLICATION_JSON));
        } catch (Exception e) {
            log.warn("SSE 发送失败: event={}", event, e);
            completeWithError(emitter, e);
        }
    }

    public static void thinking(SseEmitter emitter, String text) { send(emitter, "thinking", Map.of("text", text)); }
    public static void text(SseEmitter emitter, String text) { send(emitter, "text", Map.of("text", text)); }
    public static void toolCall(SseEmitter emitter, String tool, Map<String, Object> params) { send(emitter, "tool_call", Map.of("tool", tool, "params", params)); }
    public static void toolResult(SseEmitter emitter, String tool, Object result) { send(emitter, "tool_result", Map.of("tool", tool, "result", result)); }
    public static void data(SseEmitter emitter, Object schema, java.util.List<java.util.Map<String, Object>> dataList) { send(emitter, "data", Map.of("schema", schema, "data", dataList)); }
    public static void ui(SseEmitter emitter, com.asialjim.microapplet.commons.sse.payload.UiPayload payload) { send(emitter, "ui", payload); }
    public static void error(SseEmitter emitter, String code, String message) { send(emitter, "error", Map.of("code", code, "message", message)); }
    public static void requireInput(SseEmitter emitter, String question, String hint) { send(emitter, "require_input", Map.of("question", question, "hint", hint)); }

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
