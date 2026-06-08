package com.asialjim.microapplet.mams.aigateway.tool;

import org.junit.jupiter.api.Test;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;

/** MCP Server 自动发现 + 工具调用集成测试 — P5-MCP-03 */
class McpDiscoveryServiceTest {

    @Test void registerAndDiscover() {
        var svc = new McpDiscoveryService();
        assertEquals(0, svc.getServers().size());

        svc.register(new McpDiscoveryService.McpServer("order-service", "http://order:8080/mcp"));
        assertEquals(1, svc.getServers().size());
        assertEquals("order-service", svc.getServers().get(0).getName());
    }

    @Test void registerMultiple() {
        var svc = new McpDiscoveryService();
        svc.register(new McpDiscoveryService.McpServer("a", "http://a/mcp"));
        svc.register(new McpDiscoveryService.McpServer("b", "http://b/mcp"));
        assertEquals(2, svc.getServers().size());
    }

    @Test void discoverNoOp() {
        var svc = new McpDiscoveryService();
        svc.discover(); // 不应抛异常
        assertTrue(true);
    }

    @Test void getToolsReturnsEmpty() {
        var svc = new McpDiscoveryService();
        assertTrue(svc.getTools("any").isEmpty());
    }

    @Test void mcpServerPojo() {
        var s = new McpDiscoveryService.McpServer("svc", "http://svc/mcp");
        s.setMetadata(Map.of("version", "1.0"));
        assertEquals("svc", s.getName());
        assertEquals("http://svc/mcp", s.getEndpoint());
        assertEquals("1.0", s.getMetadata().get("version"));
    }

    @Test void mcpInvoker() {
        var invoker = new McpInvoker();
        var result = invoker.invoke("http://test/mcp", "query", Map.of("id","123"));
        assertEquals("ok", result.get("status"));
        assertEquals("query", result.get("tool"));
    }
}
