package com.asialjim.microapplet.mams.aigateway.router;

import com.asialjim.microapplet.mams.aigateway.intent.IntentResult;
import org.junit.jupiter.api.Test;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;

/** 路由配置验证 — P3-ROUTER-03 */
class NacosRouterIntegrationTest {

    @Test void configOverrideDefaultChannel() {
        var router = new Router();

        // 模拟 Nacos 下发配置：query_order → DIFY
        router.updateRules(Map.of("query_order", new RouterRule("query_order", Channel.DIFY)));

        var intent = new IntentResult("query_order", 0.8, "direct_mcp", Map.of(), "rule");
        assertEquals(Channel.DIFY, router.route(intent));
    }

    @Test void unconfiguredIntentFallsBackToDefault() {
        var router = new Router();
        router.updateRules(Map.of("query_order", new RouterRule("query_order", Channel.DIFY)));

        var intent = new IntentResult("greeting", 0.9, "ai_direct", Map.of(), "rule");
        assertEquals(Channel.AI_DIRECT, router.route(intent));
    }

    @Test void emptyConfigFallsBackToDefault() {
        var router = new Router();
        router.updateRules(Map.of());

        var intent = new IntentResult("query_order", 0.8, "direct_mcp", Map.of(), "rule");
        assertEquals(Channel.DIRECT_MCP, router.route(intent));
    }

    @Test void nullConfigFallsBack() {
        var router = new Router();
        router.updateRules(null);

        var intent = new IntentResult("query_order", 0.8, "dify", Map.of(), "rule");
        assertEquals(Channel.DIFY, router.route(intent));
    }

    @Test void configChangeReloadHotSwap() {
        var router = new Router();

        router.updateRules(Map.of("a", new RouterRule("a", Channel.DIFY)));
        assertEquals(Channel.DIFY, router.route(new IntentResult("a", 0.5, "direct_mcp", Map.of(), "")));

        // 热加载：变更配置
        router.updateRules(Map.of("a", new RouterRule("a", Channel.DIRECT_MCP)));
        assertEquals(Channel.DIRECT_MCP, router.route(new IntentResult("a", 0.5, "direct_mcp", Map.of(), "")));
    }

    @Test void configErrorChannelFallback() {
        var router = new Router();
        // 模拟错误配置：不存在的通道名
        router.updateRules(Map.of("bad", new RouterRule("bad", Channel.fromString("NONEXISTENT"))));
        var intent = new IntentResult("bad", 0.5, "ai_direct", Map.of(), "rule");
        assertEquals(Channel.AI_DIRECT, router.route(intent));
    }

    @Test void multipleRules() {
        var router = new Router();
        router.updateRules(Map.of(
            "query_order", new RouterRule("query_order", Channel.DIRECT_MCP),
            "send_message", new RouterRule("send_message", Channel.DIFY),
            "greeting", new RouterRule("greeting", Channel.AI_DIRECT)
        ));

        assertEquals(Channel.DIRECT_MCP, router.route(new IntentResult("query_order", 0.5, "", Map.of(), "")));
        assertEquals(Channel.DIFY, router.route(new IntentResult("send_message", 0.5, "", Map.of(), "")));
        assertEquals(Channel.AI_DIRECT, router.route(new IntentResult("greeting", 0.5, "", Map.of(), "")));
    }
}
