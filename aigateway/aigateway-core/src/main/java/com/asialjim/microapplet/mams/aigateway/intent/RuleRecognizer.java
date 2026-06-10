package com.asialjim.microapplet.mams.aigateway.intent;

import com.asialjim.microapplet.mams.aigateway.config.AimamsProperties;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Component
public class RuleRecognizer implements IntentRecognizer {
    private static final Logger log = LoggerFactory.getLogger(RuleRecognizer.class);
    private final List<CompiledRule> rules = new CopyOnWriteArrayList<>();
    private final AimamsProperties properties;
    private final IntentRecognitionEngine engine;

    public RuleRecognizer(AimamsProperties p, IntentRecognitionEngine e) { this.properties = p; this.engine = e; }

    @PostConstruct
    public void init() {
        List<AimamsProperties.RuleConfig> configs = properties.getIntent().getRules();
        if (configs != null) for (AimamsProperties.RuleConfig c : configs) rules.add(new CompiledRule(c));
        rules.sort(Comparator.comparingInt(CompiledRule::getPriority));
        engine.register(this);
        log.info("RuleRecognizer 已初始化: {} 条规则", rules.size());
    }

    @Override public String name() { return "rule_recognizer"; }

    @Override
    public IntentResult recognize(String message) {
        if (message == null || message.isBlank()) return null;
        for (CompiledRule rule : rules) {
            if (rule.matches(message)) {
                Map<String, Object> params = new HashMap<>(); params.put("raw", message);
                extractParams(rule, message, params);
                return new IntentResult(rule.getIntent(), 0.8, rule.getChannel(), params, name());
            }
        }
        return null;
    }

    private void extractParams(CompiledRule rule, String message, Map<String, Object> params) {
        String best = "";
        for (String kw : rule.getKeywords()) if (kw.length() > best.length() && message.contains(kw)) best = kw;
        if (!best.isEmpty()) {
            String after = message.substring(message.indexOf(best) + best.length()).trim().replaceAll("[的着了过吧呢]$", "").trim();
            if (!after.isEmpty()) params.put("target", after);
        }
    }

    static class CompiledRule {
        private final String intent; private final List<String> keywords; private final Pattern regex;
        private final String channel; private final int priority;
        CompiledRule(AimamsProperties.RuleConfig c) {
            this.intent = c.getIntent();
            this.keywords = c.getKeywords() != null ? c.getKeywords().stream().map(String::toLowerCase).collect(Collectors.toList()) : List.of();
            this.regex = c.getRegex() != null ? Pattern.compile(c.getRegex(), Pattern.CASE_INSENSITIVE) : null;
            this.channel = c.getChannel() != null ? c.getChannel() : "ai_direct";
            this.priority = c.getPriority();
        }
        boolean matches(String msg) {
            String lower = msg.toLowerCase();
            if (regex != null && regex.matcher(msg).find()) return true;
            for (String kw : keywords) if (lower.contains(kw)) return true;
            return false;
        }
        String getIntent() { return intent; }
        List<String> getKeywords() { return keywords; }
        String getChannel() { return channel; }
        int getPriority() { return priority; }
    }
}
