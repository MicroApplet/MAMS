package com.asialjim.microapplet.mams.aigateway.session;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class SessionManagerTest {

    @Test void getOrCreate() {
        var mgr = new SessionManager();
        var s = mgr.getOrCreate("s1", "u1", "wechat");
        assertEquals("s1", s.getSessionId());
        assertEquals("u1", s.getUserId());
        assertEquals("wechat", s.getPlatform());
    }

    @Test void getOrCreateReturnsCached() {
        var mgr = new SessionManager();
        var s1 = mgr.getOrCreate("s1", "u1", "wx");
        var s2 = mgr.getOrCreate("s1", "u2", "h5"); // 参数被忽略
        assertSame(s1, s2);
        assertEquals("u1", s2.getUserId()); // 第一次的值
    }

    @Test void get() {
        var mgr = new SessionManager();
        assertNull(mgr.get("nonexistent"));
        mgr.getOrCreate("s1", "u1", "wx");
        assertNotNull(mgr.get("s1"));
    }

    @Test void remove() {
        var mgr = new SessionManager();
        mgr.getOrCreate("s1", "u1", "wx");
        mgr.remove("s1");
        assertNull(mgr.get("s1"));
    }

    @Test void count() {
        var mgr = new SessionManager();
        assertEquals(0, mgr.count());
        mgr.getOrCreate("s1", "u1", "wx");
        assertEquals(1, mgr.count());
        mgr.getOrCreate("s2", "u2", "h5");
        assertEquals(2, mgr.count());
    }
}
