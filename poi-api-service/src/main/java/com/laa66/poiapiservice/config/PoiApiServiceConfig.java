package com.laa66.poiapiservice.config;

import com.laa66.poiapiservice.service.PoiApiService;
import com.laa66.poiapiservice.service.impl.PoiMapboxService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.DefaultUriBuilderFactory;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Configuration
public class PoiApiServiceConfig {

    @Value("${api.mapbox.secret-token}")
    private String secretToken;

    @Value("${api.mapbox.base-url}")
    private String baseUrl;

    @Bean
    public WebClient webClient() {
        return WebClient.builder()
                .uriBuilderFactory(new DefaultUriBuilderFactory(UriComponentsBuilder.newInstance()
                        .uri(URI.create(baseUrl))
                        .queryParam("access_token", secretToken)
                        .queryParam("limit", "25")))
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

    @Bean
    public PoiApiService poiApiService(WebClient webClient) {
        return new PoiMapboxService(webClient);
    }

}
