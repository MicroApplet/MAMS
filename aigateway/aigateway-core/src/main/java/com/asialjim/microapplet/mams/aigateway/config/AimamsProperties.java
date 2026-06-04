package com.asialjim.microapplet.mams.aigateway.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import java.util.ArrayList;
import java.util.List;

@Configuration
@ConfigurationProperties(prefix = "aimams")
public class AimamsProperties {
    private IntentConfig intent = new IntentConfig();
    private ChannelConfig channel = new ChannelConfig();
    public IntentConfig getIntent() { return intent; } public void setIntent(IntentConfig i) { this.intent = i; }
    public ChannelConfig getChannel() { return channel; } public void setChannel(ChannelConfig c) { this.channel = c; }

    public static class IntentConfig {
        private List<RuleConfig> rules = new ArrayList<>();
        private String llmEndpoint; private String apiKey; private String llmModel = "deepseek-chat";
        public List<RuleConfig> getRules() { return rules; } public void setRules(List<RuleConfig> r) { this.rules = r; }
        public String getLlmEndpoint() { return llmEndpoint; } public void setLlmEndpoint(String e) { this.llmEndpoint = e; }
        public String getApiKey() { return apiKey; } public void setApiKey(String k) { this.apiKey = k; }
        public String getLlmModel() { return llmModel; } public void setLlmModel(String m) { this.llmModel = m; }
    }

    public static class RuleConfig {
        private String intent; private List<String> keywords; private String regex;
        private String channel = "ai_direct"; private int priority = 100;
        public String getIntent() { return intent; } public void setIntent(String i) { this.intent = i; }
        public List<String> getKeywords() { return keywords; } public void setKeywords(List<String> k) { this.keywords = k; }
        public String getRegex() { return regex; } public void setRegex(String r) { this.regex = r; }
        public String getChannel() { return channel; } public void setChannel(String c) { this.channel = c; }
        public int getPriority() { return priority; } public void setPriority(int p) { this.priority = p; }
    }

    public static class ChannelConfig {
        private DifyConfig dify = new DifyConfig();
        private AiDirectConfig aiDirect = new AiDirectConfig();
        public DifyConfig getDify() { return dify; } public void setDify(DifyConfig d) { this.dify = d; }
        public AiDirectConfig getAiDirect() { return aiDirect; } public void setAiDirect(AiDirectConfig a) { this.aiDirect = a; }
    }

    public static class DifyConfig { private String url; private String apiKey;
        public String getUrl() { return url; } public void setUrl(String u) { this.url = u; }
        public String getApiKey() { return apiKey; } public void setApiKey(String k) { this.apiKey = k; } }

    public static class AiDirectConfig { private String model = "deepseek-v3"; private String endpoint;
        public String getModel() { return model; } public void setModel(String m) { this.model = m; }
        public String getEndpoint() { return endpoint; } public void setEndpoint(String e) { this.endpoint = e; } }
}
