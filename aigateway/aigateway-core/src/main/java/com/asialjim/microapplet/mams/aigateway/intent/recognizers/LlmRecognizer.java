package com.asialjim.microapplet.mams.aigateway.intent.recognizers;

import com.asialjim.microapplet.mams.aigateway.config.AimamsProperties;
import com.asialjim.microapplet.mams.aigateway.intent.IntentRecognitionEngine;
import com.asialjim.microapplet.mams.aigateway.intent.IntentRecognizer;
import com.asialjim.microapplet.mams.aigateway.intent.IntentResult;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import java.util.HashMap;
import java.util.Map;

@Component
public class LlmRecognizer implements IntentRecognizer {
    private static final Logger log = LoggerFactory.getLogger(LlmRecognizer.class);
    private final AimamsProperties properties;
    private final IntentRecognitionEngine engine;
    public LlmRecognizer(AimamsProperties p, IntentRecognitionEngine e) { this.properties = p; this.engine = e; }

    @PostConstruct
    public void init() {
        engine.register(this);
        String ep = properties.getIntent().getLlmEndpoint();
        if (ep != null && !ep.isBlank()) log.info("LlmRecognizer 已注册, endpoint={}", ep);
        else log.info("LlmRecognizer 已注册（未配置 endpoint，跳过 LLM 识别）");
    }

    @Override public String name() { return "llm_recognizer"; }

    @Override
    public IntentResult recognize(String message) {
        if (message == null || message.isBlank()) return null;
        String endpoint = properties.getIntent().getLlmEndpoint();
        if (endpoint == null || endpoint.isBlank()) return null;
        try {
            log.debug("LLM 意图识别: message={}", message);
            return simulateLlmCall(message);
        } catch (Exception e) { log.warn("LLM 意图识别失败: {}", e.getMessage()); return null; }
    }

    private IntentResult simulateLlmCall(String message) {
        String lower = message.toLowerCase(); Map<String, Object> params = new HashMap<>();
        String intent; double confidence;
        if (lower.contains("订单")||lower.contains("查订单")||lower.contains("购买")||lower.contains("下单"))
        { intent="query_order"; confidence=0.85; params.put("raw",message);
          if (lower.contains("最近")||lower.contains("最新")) params.put("scope","recent"); }
        else if (lower.contains("用户")||lower.contains("查一下")||lower.contains("查询"))
        { intent="query_user"; confidence=0.80; params.put("raw",message); }
        else if (lower.contains("发送")||lower.contains("通知")||lower.contains("发给"))
        { intent="send_message"; confidence=0.82; params.put("raw",message); }
        else if (lower.contains("帮助")||lower.contains("功能")||lower.contains("能做什么"))
        { intent="help"; confidence=0.90; }
        else if (lower.contains("你好")||lower.contains("您好")||lower.matches("^(hi|hello)\\b.*"))
        { intent="greeting"; confidence=0.95; }
        else { intent="unknown"; confidence=0.5; }
        return new IntentResult(intent, confidence, "ai_direct", params, name());
    }
}
