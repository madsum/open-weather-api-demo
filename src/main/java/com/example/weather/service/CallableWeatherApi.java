package com.example.weather.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.concurrent.Callable;

@Slf4j
public class CallableWeatherApi implements Callable<String> {
    private RestTemplate restTemplate;
    protected volatile URI uri;

    public CallableWeatherApi(RestTemplate restTemplate, URI uri) {
        this.uri = uri;
        this.restTemplate = restTemplate;
    }

    @Override
    public String call() {
        var result = restTemplate.getForEntity(uri, String.class);
        log.info(result.getBody());
        return result.getBody();
    }
}
