package com.test.general_backend_java.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/test")
public class TesterController {
    @GetMapping("/token-security")
    public ResponseEntity<Map<String, Object>> testerTokenSecurity() {
        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("message", "Hello, World!");
        responseMap.put("status", "success");
        responseMap.put("code", 200);

        return ResponseEntity.ok(responseMap);
    }
}
