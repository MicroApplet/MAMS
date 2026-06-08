package com.asialjim.microapplet.mams.aigateway.router;

import com.asialjim.microapplet.mams.aigateway.intent.IntentResult;
import org.junit.jupiter.api.Test;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;

class RouterTest {

    @Test void noRuleUsesDefaultChannel() {
        var router = new Router();
        var intent = new IntentResult("query_order", 0.8, "direct_mcp", Map.of(), "rule");
        assertEquals(Channel.DIRECT_MCP, router.route(intent));
    }

    @Test void ruleOverrideDefault() {
        var router = new Router();
        var intent = new IntentResult("query_order", 0.8, "direct_mcp", Map.of(), "rule");
        router.updateRules(Map.of("query_order", new RouterRule("query_order", Channel.DIFY)));
        assertEquals(Channel.DIFY, router.route(intent));
    }

    @Test void unknownIntentDefault() {
        var router = new Router();
        var intent = new IntentResult("unknown", 0.0, "ai_direct", Map.of(), "default");
        assertEquals(Channel.AI_DIRECT, router.route(intent));
    }

    @Test void nullIntentDefaults() {
        var router = new Router();
        assertEquals(Channel.AI_DIRECT, router.route(null));
    }

    @Test void updateRulesClearsOld() {
        var router = new Router();
        router.updateRules(Map.of("a", new RouterRule("a", Channel.DIFY)));
        router.updateRules(Map.of("b", new RouterRule("b", Channel.DIRECT_MCP)));
        assertEquals(Channel.DIRECT_MCP, router.route(new IntentResult("b", 0.5, "ai_direct", Map.of(), "")));
        assertEquals(Channel.AI_DIRECT, router.route(new IntentResult("a", 0.5, "ai_direct", Map.of(), "")));
    }

    @Test void getRulesReturnsCopy() {
        var router = new Router();
        router.updateRules(Map.of("x", new RouterRule("x", Channel.DIFY)));
        assertFalse(router.getRules().isEmpty());
    }
}
