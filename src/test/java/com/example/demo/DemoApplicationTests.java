package com.example.demo;

import com.example.demo.model.Quote;
import com.example.demo.service.RestService;
import com.example.demo.service.WebClientService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest
class DemoApplicationTests {
	private static final Logger log = LoggerFactory.getLogger(DemoApplicationTests.class);

	@Autowired
	RestService restService;
	@Autowired
	WebClientService webClientService;

	@Test
	void contextLoads() {
	}

	@Test
	void quoteTest() {
		Quote quote = restService.request("https://quoters.apps.pcfone.io/api/random", HttpMethod.GET, new ParameterizedTypeReference<>() {});
		log.info(quote.toString());
	}

	@Test
	void quoteTest2() {
		Quote quote = webClientService.request("https://quoters.apps.pcfone.io/api/random", HttpMethod.GET, new ParameterizedTypeReference<>() {});
		log.info(quote.toString());
	}

	@Test
	void webClientTest() {
		WebTestClient testClient = WebTestClient
				.bindToServer()
				.baseUrl("https://quoters.apps.pcfone.io/api/random")
				.build();

		testClient
				.get()
				.exchange()
				.expectStatus().isOk()
				.expectHeader().valueEquals("Content-Type", "application/json");
	}
}
