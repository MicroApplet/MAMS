package com.asialjim.microapplet.mams.aigateway.intent;

public interface IntentRecognizer {
    String name();
    IntentResult recognize(String message);
}
