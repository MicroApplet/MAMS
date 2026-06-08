package com.asialjim.microapplet.mams.aigateway.session;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.stereotype.Component;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 会话管理器（内存版）— 带审计日志
 */
@Component
@ConditionalOnMissingBean(name = "redisSessionManager")
public class SessionManager {
    private static final Logger audit = LoggerFactory.getLogger("AUDIT_SESSION");
    private static final Logger log = LoggerFactory.getLogger(SessionManager.class);
    private final Map<String, Session> sessions = new ConcurrentHashMap<>();

    public Session getOrCreate(String sessionId, String userId, String platform) {
        return sessions.computeIfAbsent(sessionId, k -> {
            Session s = new Session();
            s.setSessionId(sessionId);
            s.setUserId(userId);
            s.setPlatform(platform);
            audit.info("CREATE session={}, userId={}, platform={}", sessionId, userId, platform);
            return s;
        });
    }

    public Session get(String sessionId) {
        Session s = sessions.get(sessionId);
        if (s != null) {
            audit.debug("ACCESS session={}, userId={}", sessionId, s.getUserId());
        }
        return s;
    }

    public void remove(String sessionId) {
        Session s = sessions.get(sessionId);
        if (s != null) {
            audit.info("REMOVE session={}, userId={}", sessionId, s.getUserId());
        }
        sessions.remove(sessionId);
    }

    public int count() {
        int n = sessions.size();
        log.debug("SESSION_COUNT={}", n);
        return n;
    }
}
