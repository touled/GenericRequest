package com.example.demo.service;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import org.springframework.web.reactive.function.client.WebClient.RequestHeadersUriSpec;
import org.springframework.web.reactive.function.client.WebClient.RequestBodyUriSpec;
import reactor.core.publisher.Mono;

import static org.springframework.http.MediaType.APPLICATION_JSON;


@Component
public class WebClientService {
    private final WebClient webClient;

    public WebClientService(WebClient webClient) {
        this.webClient = webClient;
    }

    public <T> T request(String url, HttpMethod method, ParameterizedTypeReference<T> responseType) {

        Mono<T> response = webClient
                .method(method)
                .uri(url)
                .accept(APPLICATION_JSON)
                .retrieve()
                .bodyToMono(responseType);
        return response.block();
    }
}
