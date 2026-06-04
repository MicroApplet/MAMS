package com.asialjim.microapplet.mams.aigateway.session;

public class IntentContext {
    private String intentId;
    private String intentName;
    private java.util.Map<String, Object> output;
    public String getIntentId() { return intentId; }
    public IntentContext setIntentId(String intentId) { this.intentId = intentId; return this; }
    public String getIntentName() { return intentName; }
    public IntentContext setIntentName(String intentName) { this.intentName = intentName; return this; }
    public java.util.Map<String, Object> getOutput() { return output; }
    public IntentContext setOutput(java.util.Map<String, Object> output) { this.output = output; return this; }
}
