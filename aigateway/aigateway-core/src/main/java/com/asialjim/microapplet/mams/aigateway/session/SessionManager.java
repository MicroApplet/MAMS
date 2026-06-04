package com.asialjim.microapplet.mams.aigateway.session;

import org.springframework.stereotype.Component;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class SessionManager {
    private final Map<String, Session> sessions = new ConcurrentHashMap<>();
    public Session getOrCreate(String sessionId, String userId, String platform) {
        return sessions.computeIfAbsent(sessionId, k -> { Session s = new Session(); s.setSessionId(sessionId); s.setUserId(userId); s.setPlatform(platform); return s; });
    }
    public Session get(String sessionId) { return sessions.get(sessionId); }
    public void remove(String sessionId) { sessions.remove(sessionId); }
    public int count() { return sessions.size(); }
}
