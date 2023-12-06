package com.laa66.poiapiservice.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import com.laa66.poiapiservice.config.PoiApiServiceConfig;
import com.laa66.poiapiservice.domain.*;
import com.laa66.poiapiservice.service.PoiApiService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;


import java.util.List;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = PoiApiServiceConfig.class)
@WireMockTest(httpPort = 8088)
class PoiMapboxServiceIntegrationTest {

    @DynamicPropertySource
    static void configure(DynamicPropertyRegistry registry) {
        registry.add("api.mapbox.secret-token", () -> "mock_token");
        registry.add("api.mapbox.base-url", () -> "http://localhost:8088");
    }

    @Autowired
    PoiApiService poiApiService;

    ObjectMapper mapper = new ObjectMapper();

    @Test
    void shouldGetPoiCollectionStatus2xx() throws JsonProcessingException {
        PoiApiResponse apiResponse = new PoiApiResponse("car", List.of(
                new Feature("point", new Geometry(new double[]{17.3, 51.3}, "type"), new Properties("name", "address", new String[0]))));

        stubFor(get("/search/searchbox/v1/category/car?access_token=mock_token&limit=25&proximity=17.3,51.3&poi_category_exclusions=services")
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .withBody(mapper.writeValueAsString(apiResponse))));

        Flux<TripPoint> response = poiApiService.getPoiCollection("car", 17.3, 51.3);
        StepVerifier.create(response)
                .assertNext(tripPoint -> assertAll(
                        () -> assertEquals(17.3, tripPoint.longitude()),
                        () -> assertEquals(51.3, tripPoint.latitude()),
                        () -> assertEquals("name", tripPoint.name()),
                        () -> assertEquals("address", tripPoint.fullAddress())
                ))
                .verifyComplete();

        verify(exactly(1), getRequestedFor(urlEqualTo("/search/searchbox/v1/category/car?access_token=mock_token&limit=25&proximity=17.3,51.3&poi_category_exclusions=services")));
    }

    @Test
    void shouldGetPoiCollectionStatus4xx() {
        stubFor(get("/search/searchbox/v1/category/wrong_category?access_token=mock_token&limit=25&proximity=17.3,51.3&poi_category_exclusions=services")
                .willReturn(aResponse()
                        .withStatus(403)));

        Flux<TripPoint> response = poiApiService.getPoiCollection("wrong_category", 17.3, 51.3);
        StepVerifier.create(response)
                .expectError(WebClientResponseException.class)
                .verify();

        verify(exactly(1), getRequestedFor(urlEqualTo("/search/searchbox/v1/category/wrong_category?access_token=mock_token&limit=25&proximity=17.3,51.3&poi_category_exclusions=services")));
    }


}