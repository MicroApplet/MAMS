package com.asialjim.microapplet.mams.aigateway.intent;

import java.util.Map;

public class IntentResult {
    private String intent;
    private double confidence;
    private String defaultChannel;
    private Map<String, Object> params;
    private String source;
    public IntentResult() {}
    public IntentResult(String intent, double confidence, String defaultChannel, Map<String, Object> params, String source) {
        this.intent = intent; this.confidence = confidence; this.defaultChannel = defaultChannel;
        this.params = params; this.source = source;
    }
    public String getIntent() { return intent; }
    public void setIntent(String intent) { this.intent = intent; }
    public double getConfidence() { return confidence; }
    public void setConfidence(double confidence) { this.confidence = confidence; }
    public String getDefaultChannel() { return defaultChannel; }
    public void setDefaultChannel(String defaultChannel) { this.defaultChannel = defaultChannel; }
    public Map<String, Object> getParams() { return params; }
    public void setParams(Map<String, Object> params) { this.params = params; }
    public String getSource() { return source; }
    public void setSource(String source) { this.source = source; }
}
