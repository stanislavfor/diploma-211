package com.example.service;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class AuthService {

    private final RestTemplate restTemplate;

    public AuthService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String authenticate(String username, String password) {
        String url = "http://localhost:8081/images/my-app";  // URL для аутентификации

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");

        String requestBody = String.format("{\"username\":\"%s\", \"password\":\"%s\"}", username, password);

        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);

        HttpHeaders responseHeaders = response.getHeaders();
        String setCookie = responseHeaders.getFirst(HttpHeaders.SET_COOKIE);

        if (setCookie != null && setCookie.contains("JSESSIONID")) {
            return setCookie.split(";")[0].split("=")[1];
        } else {
            throw new RuntimeException("JSESSIONID not found in the response");
        }
    }
}
