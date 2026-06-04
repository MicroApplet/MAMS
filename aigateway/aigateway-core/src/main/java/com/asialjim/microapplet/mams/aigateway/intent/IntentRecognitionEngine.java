package com.asialjim.microapplet.mams.aigateway.intent;

import com.asialjim.microapplet.mams.aigateway.session.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

@Component
public class IntentRecognitionEngine {
    private static final Logger log = LoggerFactory.getLogger(IntentRecognitionEngine.class);
    private final List<IntentRecognizer> recognizers = new CopyOnWriteArrayList<>();

    public void register(IntentRecognizer recognizer) {
        this.recognizers.add(recognizer);
        log.info("意图识别器已注册: {}", recognizer.name());
    }

    public IntentResult recognize(String message, Session session) {
        if (recognizers.isEmpty()) { log.warn("无可用的意图识别器"); return defaultResult(message); }
        IntentResult best = null;
        for (IntentRecognizer r : recognizers) {
            try {
                IntentResult result = r.recognize(message, session);
                if (result == null) continue;
                if (best == null || result.getConfidence() > best.getConfidence()) best = result;
            } catch (Exception e) { log.warn("识别器 {} 异常: {}", r.name(), e.getMessage()); }
        }
        if (best == null) return defaultResult(message);
        log.info("意图识别结果: intent={}, confidence={}, source={}", best.getIntent(), best.getConfidence(), best.getSource());
        return best;
    }

    private IntentResult defaultResult(String message) {
        Map<String, Object> p = new HashMap<>(); p.put("raw", message);
        return new IntentResult("unknown", 0.0, "ai_direct", p, "default");
    }
}
