package com.asialjim.microapplet.mams.aigateway.tool;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

@Component
public class McpDiscoveryService {
    private static final Logger log = LoggerFactory.getLogger(McpDiscoveryService.class);
    private final List<McpServer> servers = new CopyOnWriteArrayList<>();

    public void discover() { log.info("MCP 服务发现已触发（模拟，待 Nacos 对接）"); }
    public void register(McpServer server) { this.servers.add(server); log.info("MCP Server 已注册: {} -> {}", server.getName(), server.getEndpoint()); }
    public List<McpServer> getServers() { return List.copyOf(servers); }
    public List<String> getTools(String serviceName) { return List.of(); }

    public static class McpServer {
        private String name; private String endpoint; private Map<String, String> metadata;
        public McpServer() {}
        public McpServer(String n, String e) { this.name = n; this.endpoint = e; }
        public String getName() { return name; } public void setName(String n) { this.name = n; }
        public String getEndpoint() { return endpoint; } public void setEndpoint(String e) { this.endpoint = e; }
        public Map<String, String> getMetadata() { return metadata; } public void setMetadata(Map<String, String> m) { this.metadata = m; }
    }
}
