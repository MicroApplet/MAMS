package com.asialjim.microapplet.mams.aigateway.router;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import java.util.Map;

@Component
public class NacosRouterConfigListener {
    private static final Logger log = LoggerFactory.getLogger(NacosRouterConfigListener.class);
    private final Router router;
    private final ObjectMapper mapper;
    public NacosRouterConfigListener(Router r, ObjectMapper m) { this.router = r; this.mapper = m; }

    @PostConstruct
    public void init() {
        log.info("Nacos 路由监听已启动（dataId: aimams-router.yaml, group: AIMAMS）");
        loadDefaultRules();
    }

    private void loadDefaultRules() { log.info("已加载默认路由规则（模拟），等待 Nacos 配置下发"); }

    @SuppressWarnings("unchecked")
    public void onConfigChange(String config) {
        try {
            Map<String, Object> root = mapper.readValue(config, new TypeReference<Map<String, Object>>() {});
            Map<String, Object> routerCfg = (Map<String, Object>) root.get("router");
            if (routerCfg == null) { log.warn("路由配置为空"); return; }
            Map<String, Object> rulesMap = (Map<String, Object>) routerCfg.get("rules");
            if (rulesMap == null || rulesMap.isEmpty()) { router.updateRules(null); return; }
            java.util.Map<String, RouterRule> newRules = new java.util.HashMap<>();
            for (Map.Entry<String, Object> entry : rulesMap.entrySet()) {
                Map<String, String> rc = (Map<String, String>) entry.getValue();
                String ch = rc.get("channel");
                if (ch != null) newRules.put(entry.getKey(), new RouterRule(entry.getKey(), Channel.fromString(ch)));
            }
            router.updateRules(newRules);
            log.info("路由规则已热更新: {} 条", newRules.size());
        } catch (Exception e) { log.error("解析路由配置失败", e); }
    }
}
