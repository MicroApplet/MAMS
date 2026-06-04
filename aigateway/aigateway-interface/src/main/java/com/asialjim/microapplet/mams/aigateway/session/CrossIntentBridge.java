package com.asialjim.microapplet.mams.aigateway.session;

import java.util.*;

public class CrossIntentBridge {
    private final String fromIntentId;
    private final String toIntentId;
    private final List<String> sharedKeys;

    public CrossIntentBridge(String fromIntentId, String toIntentId, List<String> sharedKeys) {
        this.fromIntentId = fromIntentId;
        this.toIntentId = toIntentId;
        this.sharedKeys = sharedKeys != null ? List.copyOf(sharedKeys) : List.of();
    }
    public String getFromIntentId() { return fromIntentId; }
    public String getToIntentId() { return toIntentId; }
    public List<String> getSharedKeys() { return sharedKeys; }
    public Map<String, Object> resolve(IntentContext fromContext) {
        if (fromContext == null || fromContext.getOutput() == null) return Collections.emptyMap();
        Map<String, Object> result = new HashMap<>();
        for (String key : sharedKeys) {
            if (fromContext.getOutput().containsKey(key)) result.put(key, fromContext.getOutput().get(key));
        }
        return Collections.unmodifiableMap(result);
    }
}
