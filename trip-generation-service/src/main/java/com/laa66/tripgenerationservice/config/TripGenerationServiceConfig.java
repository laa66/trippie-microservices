package com.laa66.tripgenerationservice.config;

import com.laa66.tripgenerationservice.service.TripGenerationService;
import com.laa66.tripgenerationservice.service.impl.TripGenerationServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class TripGenerationServiceConfig {

    @Bean
    public WebClient webClient() {
        return WebClient.builder()
                .baseUrl("http://localhost:8081")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

    @Bean
    public TripGenerationService tripGenerationService(WebClient webClient) {
        return new TripGenerationServiceImpl(webClient);
    }

}