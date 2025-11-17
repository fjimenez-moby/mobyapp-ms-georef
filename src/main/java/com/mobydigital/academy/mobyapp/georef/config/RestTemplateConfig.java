package com.mobydigital.academy.mobyapp.georef.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.time.Duration;

@Configuration
public class RestTemplateConfig {

    @Value("${georef.api.timeout.connect:5000}")
    private int connectTimeout;

    @Value("${georef.api.timeout.read:10000}")
    private int readTimeout;

    @Bean
    public RestTemplate georefRestTemplate(RestTemplateBuilder builder) {
        return builder
                .setConnectTimeout(Duration.ofMillis(connectTimeout))
                .setReadTimeout(Duration.ofMillis(readTimeout))
                .errorHandler(new DefaultResponseErrorHandler() {
                    @Override
                    public boolean hasError(ClientHttpResponse response) throws IOException {
                        HttpStatus status = (HttpStatus) response.getStatusCode();
                        return status.is5xxServerError();
                    }
                })
                .build();
    }
}