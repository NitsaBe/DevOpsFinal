package com.example.DevOps;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.beans.factory.annotation.Value;

@SpringBootApplication
@Controller
public class FrontendApplication {

    @Value("${backend.url:http://localhost:8080}")
    private String backendUrl;

    public static void main(String[] args) {
        SpringApplication.run(FrontendApplication.class, args);
    }

    @GetMapping("/")
    public String index(Model model) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            Object response = restTemplate.getForObject(backendUrl + "/api/data", Object.class);
            model.addAttribute("backendData", response);
            model.addAttribute("status", "Connected");
            model.addAttribute("backendUrl", backendUrl);
        } catch (Exception e) {
            model.addAttribute("backendData", "Unable to connect to backend");
            model.addAttribute("status", "Error: " + e.getMessage());
            model.addAttribute("backendUrl", backendUrl);
        }
        model.addAttribute("javaVersion", System.getProperty("java.version"));
        return "index";
    }
}
