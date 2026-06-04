package com.asialjim.microapplet.mams.aigateway.router;

public enum Channel {
    DIRECT_MCP,
    AI_DIRECT,
    DIFY;
    public static Channel fromString(String name) {
        if (name == null) return AI_DIRECT;
        try { return valueOf(name.toUpperCase()); }
        catch (IllegalArgumentException e) { return AI_DIRECT; }
    }
}
