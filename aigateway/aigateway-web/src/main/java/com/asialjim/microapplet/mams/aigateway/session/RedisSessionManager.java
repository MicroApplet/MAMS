package com.asialjim.microapplet.mams.aigateway.session;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * Redis 会话持久化 — 当 Redis 可用时自动启用
 */
@Component
@ConditionalOnClass(StringRedisTemplate.class)
public class RedisSessionManager {
    private static final Logger log = LoggerFactory.getLogger(RedisSessionManager.class);
    private static final String KEY_PREFIX = "aimams:session:";
    private static final long TTL_SECONDS = 1800;

    private final StringRedisTemplate redis;
    private final ObjectMapper mapper;

    public RedisSessionManager(Optional<StringRedisTemplate> redis, ObjectMapper mapper) {
        this.redis = redis.orElse(null);
        this.mapper = mapper;
        if (this.redis != null) log.info("RedisSessionManager 已启用（TTL: {}s）", TTL_SECONDS);
    }

    public Session getOrCreate(String sessionId, String userId, String platform) {
        Session s = get(sessionId);
        if (s != null) return s;
        s = new Session(); s.setSessionId(sessionId); s.setUserId(userId); s.setPlatform(platform);
        save(s); return s;
    }

    public Session get(String sessionId) {
        if (redis == null) return null;
        try {
            String val = redis.opsForValue().get(KEY_PREFIX + sessionId);
            if (val == null) return null;
            Session s = mapper.readValue(val, Session.class);
            redis.expire(KEY_PREFIX + sessionId, TTL_SECONDS, TimeUnit.SECONDS);
            return s;
        } catch (Exception e) { log.warn("Redis 读失败: {}", e.getMessage()); return null; }
    }

    public void save(Session s) {
        if (redis == null || s == null || s.getSessionId() == null) return;
        try { redis.opsForValue().set(KEY_PREFIX + s.getSessionId(), mapper.writeValueAsString(s), TTL_SECONDS, TimeUnit.SECONDS); }
        catch (Exception e) { log.warn("Redis 写失败: {}", e.getMessage()); }
    }

    public void remove(String sessionId) { if (redis != null) redis.delete(KEY_PREFIX + sessionId); }
}
