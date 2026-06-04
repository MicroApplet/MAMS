package com.asialjim.microapplet.mams.aigateway.session;

import java.util.*;

public class Session {
    private String sessionId;
    private String userId;
    private String roleBit;
    private String platform;
    private String chl;
    private String chlAppId;
    private Map<String, IntentContext> intents;
    private final List<CrossIntentBridge> bridges = new ArrayList<>();

    public String getSessionId() { return sessionId; }
    public Session setSessionId(String id) { this.sessionId = id; return this; }
    public String getUserId() { return userId; }
    public Session setUserId(String uid) { this.userId = uid; return this; }
    public String getRoleBit() { return roleBit; }
    public Session setRoleBit(String r) { this.roleBit = r; return this; }
    public String getPlatform() { return platform; }
    public Session setPlatform(String p) { this.platform = p; return this; }
    public String getChl() { return chl; }
    public Session setChl(String c) { this.chl = c; return this; }
    public String getChlAppId() { return chlAppId; }
    public Session setChlAppId(String a) { this.chlAppId = a; return this; }
    public Map<String, IntentContext> getIntents() { return intents; }
    public Session setIntents(Map<String, IntentContext> m) { this.intents = m; return this; }
    public List<CrossIntentBridge> getBridges() { return List.copyOf(bridges); }

    public IntentContext getOrCreateIntent(String intentId) {
        if (this.intents == null) this.intents = new HashMap<>();
        return this.intents.computeIfAbsent(intentId, k -> {
            IntentContext ctx = new IntentContext();
            ctx.setIntentId(intentId);
            ctx.setOutput(new HashMap<>());
            return ctx;
        });
    }

    public Optional<IntentContext> getIntent(String intentId) {
        return intents == null ? Optional.empty() : Optional.ofNullable(intents.get(intentId));
    }

    public void addBridge(String from, String to, List<String> keys) {
        boolean exists = bridges.stream().anyMatch(b -> b.getFromIntentId().equals(from) && b.getToIntentId().equals(to));
        if (!exists) bridges.add(new CrossIntentBridge(from, to, keys));
    }

    public Map<String, Object> resolveSharedData(String toIntentId) {
        Map<String, Object> merged = new HashMap<>();
        for (CrossIntentBridge bridge : bridges) {
            if (bridge.getToIntentId().equals(toIntentId)) {
                IntentContext fromCtx = intents != null ? intents.get(bridge.getFromIntentId()) : null;
                if (fromCtx != null) merged.putAll(bridge.resolve(fromCtx));
            }
        }
        return merged;
    }
}
