package com.asialjim.microapplet.mams.aigateway.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.time.Instant;
import java.util.Map;

@RestController
public class IndexController {
    @GetMapping({"/","/health"})
    public Map<String,Object> health() {
        return Map.of("service","AI Gateway","version","1.0.0","status","running","timestamp",Instant.now().toString());
    }
}
