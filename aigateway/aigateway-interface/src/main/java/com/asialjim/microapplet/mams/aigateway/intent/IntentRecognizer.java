package com.asialjim.microapplet.mams.aigateway.intent;

import com.asialjim.microapplet.mams.aigateway.session.Session;

public interface IntentRecognizer {
    String name();
    IntentResult recognize(String message, Session session);
}
