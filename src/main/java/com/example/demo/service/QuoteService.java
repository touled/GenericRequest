package com.example.demo.service;

import com.example.demo.error.QuoteServiceException;
import com.example.demo.model.Quote;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.codec.ClientCodecConfigurer;
import org.springframework.http.codec.json.Jackson2JsonDecoder;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import static org.springframework.http.MediaType.TEXT_HTML;

public class QuoteService {
    private final WebClient webClient;

    public QuoteService(String baseUrl) {
        this.webClient = WebClient.builder()
                .baseUrl(baseUrl)
                .exchangeStrategies(
                        ExchangeStrategies.builder()
                                .codecs(this::acceptedCodecs)
                                .build())
                .build();
    }

    public Mono<Quote> getQuote() {
        return webClient
                .get()
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError, response ->
                    Mono.error(new QuoteServiceException("Client error")))
                .onStatus(HttpStatus::is5xxServerError, response ->
                    Mono.error(new QuoteServiceException("Server error")))
                .bodyToMono(Quote.class)
                .onErrorMap(throwable -> new QuoteServiceException(throwable.getMessage()));
    }

    private void acceptedCodecs(ClientCodecConfigurer clientCodecConfigurer) {
        clientCodecConfigurer
                .customCodecs()
                .register(new Jackson2JsonDecoder(new ObjectMapper(), TEXT_HTML));
    }
}
