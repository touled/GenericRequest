package com.example.demo.service;

import com.example.demo.model.Quote;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.TEXT_HTML;

class QuoteServiceTest {
    private QuoteService quoteService;
    private static MockWebServer mockBackEnd;
    private final Logger log = LoggerFactory.getLogger(QuoteServiceTest.class);

    @BeforeAll
    static void setUp() throws IOException {
        mockBackEnd = new MockWebServer();
        mockBackEnd.start();
    }

    @AfterAll
    static void tearDown() throws IOException {
        mockBackEnd.shutdown();
    }

    @BeforeEach
    void initialize() {
        String baseUrl = String.format("http://localhost:%s", mockBackEnd.getPort());
        quoteService = new QuoteService(baseUrl);
    }

    @Test
    void test1() throws JsonProcessingException {
        String json = "{\n" +
                "    \"type\" : \"type\",\n" +
                "    \"value\" : {\n" +
                "        \"id\" : 1,\n" +
                "        \"quote\" : \"Quote1\",\n" +
                "        \"another_property\" : \"Property value 1\"\n" +
                "    }\n" +
                "}";
        mockBackEnd.enqueue(new MockResponse()
                .setBody(json)
                .addHeader("Content-Type", TEXT_HTML));

        Mono<Quote> quoteMono = quoteService.getQuote();

        StepVerifier.create(quoteMono)
                .expectNextMatches(q -> q.getType().equals("type"))
                .expectComplete()
                .verify()
        ;
    }
}