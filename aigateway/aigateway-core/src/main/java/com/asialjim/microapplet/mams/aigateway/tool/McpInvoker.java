package com.asialjim.microapplet.mams.aigateway.tool;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import java.util.Map;

@Component
public class McpInvoker {
    private static final Logger log = LoggerFactory.getLogger(McpInvoker.class);
    public Map<String, Object> invoke(String endpoint, String toolName, Map<String, Object> params) {
        log.info("调用 MCP 工具: endpoint={}, tool={}", endpoint, toolName);
        return Map.of("status","ok","message","MCP 工具调用成功（模拟）","tool",toolName,"result",Map.of());
    }
}
