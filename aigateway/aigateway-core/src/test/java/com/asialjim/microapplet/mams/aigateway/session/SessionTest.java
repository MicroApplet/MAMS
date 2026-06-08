package com.asialjim.microapplet.mams.aigateway.session;

import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;

class SessionTest {

    @Test void createSession() {
        var s = new Session();
        s.setSessionId("s1").setUserId("u1").setPlatform("wechat");
        assertEquals("s1", s.getSessionId());
        assertEquals("u1", s.getUserId());
        assertEquals("wechat", s.getPlatform());
    }

    @Test void getOrCreateIntent() {
        var s = new Session();
        var ctx = s.getOrCreateIntent("query_order");
        assertNotNull(ctx);
        assertNotNull(ctx.getOutput());
        assertTrue(ctx.getOutput().isEmpty());
        // same id returns cached
        assertSame(ctx, s.getOrCreateIntent("query_order"));
    }

    @Test void addAndResolveBridge() {
        var s = new Session();
        var from = s.getOrCreateIntent("order_query");
        from.getOutput().put("orderId", "12345");
        from.getOutput().put("amount", 200);

        s.addBridge("order_query", "send_msg", List.of("orderId"));
        var shared = s.resolveSharedData("send_msg");

        assertEquals(1, shared.size());
        assertEquals("12345", shared.get("orderId"));
        assertFalse(shared.containsKey("amount"));
    }

    @Test void noBridgeReturnsEmpty() {
        var s = new Session();
        assertTrue(s.resolveSharedData("anything").isEmpty());
    }

    @Test void duplicateBridgeNoOp() {
        var s = new Session();
        s.addBridge("a", "b", List.of("x"));
        s.addBridge("a", "b", List.of("x"));
        assertEquals(1, s.getBridges().size());
    }

    @Test void getIntentOptional() {
        var s = new Session();
        assertTrue(s.getIntent("nonexistent").isEmpty());
        s.getOrCreateIntent("exists");
        assertTrue(s.getIntent("exists").isPresent());
    }

    @Test void resolveFromNullIntentContext() {
        var s = new Session();
        s.addBridge("missing", "b", List.of("key"));
        assertTrue(s.resolveSharedData("b").isEmpty());
    }
}
