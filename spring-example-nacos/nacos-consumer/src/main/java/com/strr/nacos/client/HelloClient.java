package com.strr.nacos.client;

import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class HelloClient {
    private final RestTemplate restTemplate;

    public HelloClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String hello() {
        ResponseEntity<String> exchange = restTemplate.exchange("http://nacos-provider/hello", HttpMethod.GET, null, String.class);
        return exchange.getBody();
    }
}
