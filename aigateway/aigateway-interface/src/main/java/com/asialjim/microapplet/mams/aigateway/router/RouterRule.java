package com.asialjim.microapplet.mams.aigateway.router;

public class RouterRule {
    private String intent;
    private Channel channel;
    public RouterRule() {}
    public RouterRule(String intent, Channel channel) { this.intent = intent; this.channel = channel; }
    public String getIntent() { return intent; }
    public void setIntent(String i) { this.intent = i; }
    public Channel getChannel() { return channel; }
    public void setChannel(Channel c) { this.channel = c; }
}
