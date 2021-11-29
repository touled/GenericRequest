package com.example.demo;

import com.example.demo.model.Quote;
import com.example.demo.service.RestService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SpringBootTest
class DemoApplicationTests {
	private static final Logger log = LoggerFactory.getLogger(DemoApplicationTests.class);

	@Autowired
	RestService restService;

	@Test
	void contextLoads() {
	}

	@Test
	void quoteTest() {
		Quote quote = restService.request("https://quoters.apps.pcfone.io/api/random", HttpMethod.GET, new ParameterizedTypeReference<>() {});
		log.info(quote.toString());
	}
}
