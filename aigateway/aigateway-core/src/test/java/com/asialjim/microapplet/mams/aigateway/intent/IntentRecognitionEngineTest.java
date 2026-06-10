package com.asialjim.microapplet.mams.aigateway.intent;

import org.junit.jupiter.api.Test;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

class IntentRecognitionEngineTest {

    @Test void noRecognizersReturnsDefault() {
        var engine = new IntentRecognitionEngine();
        IntentResult result = engine.recognize("hello");
        assertNotNull(result);
        assertEquals("unknown", result.getIntent());
        assertEquals(0.0, result.getConfidence());
    }

    @Test void picksHighestConfidence() {
        var engine = new IntentRecognitionEngine();

        engine.register(new IntentRecognizer() {
            public String name() { return "low"; }
            public IntentResult recognize(String msg) {
                if (msg.contains("order"))
                    return new IntentResult("order_low", 0.3, "direct_mcp", Map.of(), "low");
                return null;
            }
        });
        engine.register(new IntentRecognizer() {
            public String name() { return "high"; }
            public IntentResult recognize(String msg) {
                if (msg.contains("order"))
                    return new IntentResult("order_high", 0.9, "ai_direct", Map.of(), "high");
                return null;
            }
        });

        var result = engine.recognize("find my order");
        assertNotNull(result);
        assertEquals("order_high", result.getIntent());
        assertEquals(0.9, result.getConfidence());
        assertEquals("high", result.getSource());
    }

    @Test void allNullReturnsDefault() {
        var engine = new IntentRecognitionEngine();
        engine.register(new IntentRecognizer() {
            public String name() { return "null_rec"; }
            public IntentResult recognize(String msg) { return null; }
        });
        var result = engine.recognize("anything");
        assertEquals("unknown", result.getIntent());
    }

    @Test void recognizerExceptionFallsback() {
        var engine = new IntentRecognitionEngine();
        engine.register(new IntentRecognizer() {
            public String name() { return "broken"; }
            public IntentResult recognize(String msg) { throw new RuntimeException("boom"); }
        });
        var result = engine.recognize("test");
        assertEquals("unknown", result.getIntent());
    }

    @Test void registerMultiple() {
        var engine = new IntentRecognitionEngine();
        engine.register(new IntentRecognizer() {
            public String name() { return "a"; }
            public IntentResult recognize(String m) {
                return new IntentResult("a", 0.5, "direct_mcp", Map.of(), "a");
            }
        });
        engine.register(new IntentRecognizer() {
            public String name() { return "b"; }
            public IntentResult recognize(String m) {
                return new IntentResult("b", 0.6, "ai_direct", Map.of(), "b");
            }
        });
        var result = engine.recognize("msg");
        assertEquals("b", result.getIntent());
        assertEquals(0.6, result.getConfidence());
    }
}
