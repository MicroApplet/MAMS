package com.asialjim.microapplet.mams.aigateway.router;

import com.asialjim.microapplet.mams.aigateway.intent.IntentResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class Router {
    private static final Logger log = LoggerFactory.getLogger(Router.class);
    private final Map<String, RouterRule> rules = new ConcurrentHashMap<>();

    public void updateRules(Map<String, RouterRule> newRules) {
        rules.clear(); if (newRules != null) rules.putAll(newRules);
        log.info("路由规则已更新: {} 条", rules.size());
    }

    public Channel route(IntentResult intent) {
        if (intent == null) return Channel.AI_DIRECT;
        RouterRule rule = rules.get(intent.getIntent());
        if (rule != null) { log.debug("命中配置规则: intent={}, channel={}", intent.getIntent(), rule.getChannel()); return rule.getChannel(); }
        Channel def = Channel.fromString(intent.getDefaultChannel());
        log.debug("无配置，走默认: intent={}, channel={}", intent.getIntent(), def);
        return def;
    }

    public Map<String, RouterRule> getRules() { return Map.copyOf(rules); }
}
