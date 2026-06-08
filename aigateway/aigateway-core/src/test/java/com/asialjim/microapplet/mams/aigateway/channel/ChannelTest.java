package com.asialjim.microapplet.mams.aigateway.channel;

import com.asialjim.microapplet.mams.aigateway.router.Channel;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ChannelTest {

    @Test void fromString() {
        assertEquals(Channel.DIRECT_MCP, Channel.fromString("DIRECT_MCP"));
        assertEquals(Channel.AI_DIRECT, Channel.fromString("ai_direct"));
        assertEquals(Channel.DIFY, Channel.fromString("Dify"));
    }

    @Test void unknownDefaultsToAiDirect() {
        assertEquals(Channel.AI_DIRECT, Channel.fromString("unknown"));
        assertEquals(Channel.AI_DIRECT, Channel.fromString(null));
    }

    @Test void values() {
        assertTrue(Channel.values().length >= 3);
    }
}
