package com.asialjim.microapplet.mams.aigateway.channel;

import com.asialjim.microapplet.mams.aigateway.router.Channel;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import java.util.EnumMap;
import java.util.Map;

@Component
public class ChannelExecutorRouter {
    private static final Logger log = LoggerFactory.getLogger(ChannelExecutorRouter.class);
    private final Map<Channel, ChannelExecutor> executors = new EnumMap<>(Channel.class);

    public ChannelExecutorRouter(DirectMcpExecutor mcp, AiDirectExecutor ai, DifyDelegateExecutor dify) {
        executors.put(Channel.DIRECT_MCP, mcp); executors.put(Channel.AI_DIRECT, ai); executors.put(Channel.DIFY, dify);
    }

    @PostConstruct public void init() { log.info("通道执行器已注册: {}", executors.keySet()); }

    public ChannelExecutor getExecutor(Channel channel) {
        ChannelExecutor e = executors.get(channel);
        if (e == null) { log.warn("未找到通道 {}，回退 AI_DIRECT", channel); return executors.get(Channel.AI_DIRECT); }
        return e;
    }
}
