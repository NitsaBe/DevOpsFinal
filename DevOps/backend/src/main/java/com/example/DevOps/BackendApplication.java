package com.example.DevOps;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
@RestController
@CrossOrigin(origins = "*")
public class BackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(BackendApplication.class, args);
    }

    @GetMapping("/api/health")
    public ResponseEntity<Map<String, String>> health() {
        Map<String, String> response = new HashMap<>();
        response.put("status", "UP");
        response.put("service", "backend");
        response.put("java.version", System.getProperty("java.version"));
        return ResponseEntity.ok(response);
    }

    @GetMapping("/api/data")
    public ResponseEntity<Map<String, Object>> getData() {
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Hello from Backend with JDK 24!");
        response.put("timestamp", System.currentTimeMillis());
        response.put("javaVersion", System.getProperty("java.version"));
        response.put("data", new String[]{"item1", "item2", "item3"});
        return ResponseEntity.ok(response);
    }
}