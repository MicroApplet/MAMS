package com.asialjim.microapplet.mams.aigateway.session;

import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;

class CrossIntentBridgeTest {

    @Test void resolveSharedKeys() {
        var from = new IntentContext();
        from.setIntentId("order");
        from.setOutput(Map.of("id","12345","amount",200));

        var bridge = new CrossIntentBridge("order", "msg", List.of("id"));
        var shared = bridge.resolve(from);
        assertEquals(1, shared.size());
        assertEquals("12345", shared.get("id"));
    }

    @Test void unsharedKeysNotIncluded() {
        var from = new IntentContext();
        from.setIntentId("order");
        from.setOutput(Map.of("secret","sensitive","id","123"));

        var bridge = new CrossIntentBridge("order", "msg", List.of("id"));
        var shared = bridge.resolve(from);
        assertFalse(shared.containsKey("secret"));
    }

    @Test void nullContextReturnsEmpty() {
        var bridge = new CrossIntentBridge("a", "b", List.of("x"));
        assertTrue(bridge.resolve(null).isEmpty());
    }

    @Test void nullOutputReturnsEmpty() {
        var from = new IntentContext();
        var bridge = new CrossIntentBridge("a", "b", List.of("x"));
        assertTrue(bridge.resolve(from).isEmpty());
    }

    @Test void getter() {
        var bridge = new CrossIntentBridge("from", "to", List.of("k1","k2"));
        assertEquals("from", bridge.getFromIntentId());
        assertEquals("to", bridge.getToIntentId());
        assertEquals(2, bridge.getSharedKeys().size());
    }
}
