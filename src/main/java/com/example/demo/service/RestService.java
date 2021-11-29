package com.example.demo.service;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class RestService {
    private final RestTemplate restTemplate;

    public RestService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public <T> T request(String url, HttpMethod method, ParameterizedTypeReference<T> responseType) {
        ResponseEntity<T> response = restTemplate.exchange(url, method, null, responseType);
        return response.getBody();
    }
}
