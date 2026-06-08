package com.asialjim.microapplet.mams.aigateway.intent;

import com.asialjim.microapplet.mams.aigateway.session.Session;
import org.junit.jupiter.api.Test;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;

class IntentResultTest {

    @Test void create() {
        var r = new IntentResult("query_order", 0.85, "direct_mcp", Map.of("userId","123"), "rule");
        assertEquals("query_order", r.getIntent());
        assertEquals(0.85, r.getConfidence());
        assertEquals("direct_mcp", r.getDefaultChannel());
        assertEquals("123", r.getParams().get("userId"));
        assertEquals("rule", r.getSource());
    }

    @Test void emptyConstructor() {
        var r = new IntentResult();
        r.setIntent("help");
        assertEquals("help", r.getIntent());
    }
}
