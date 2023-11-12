package com.laa66.poiapiservice.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import com.laa66.poiapiservice.config.PoiApiServiceConfig;
import com.laa66.poiapiservice.domain.PoiApiResponse;
import com.laa66.poiapiservice.service.PoiApiService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.web.reactive.function.client.WebClientResponseException;


import java.util.List;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
        stubFor(get("/search/searchbox/v1/category/car?access_token=mock_token&limit=25&proximity=17.3,51.3")
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .withBody(mapper.writeValueAsString(new PoiApiResponse("type", List.of())))));

        PoiApiResponse response = poiApiService.getPoiCollection("car", 17.3, 51.3);

        assertEquals("type", response.type());
        assertEquals(0, response.features().size());
        verify(exactly(1), getRequestedFor(urlEqualTo("/search/searchbox/v1/category/car?access_token=mock_token&limit=25&proximity=17.3,51.3")));
    }

    @Test
    void shouldGetPoiCollectionStatus4xx() {
        stubFor(get("/search/searchbox/v1/category/wrong_category?access_token=mock_token&limit=25&proximity=17.3,51.3")
                .willReturn(aResponse()
                        .withStatus(403)));

        assertThrows(WebClientResponseException.class, () -> poiApiService.getPoiCollection("wrong_category", 17.3, 51.3));
        verify(exactly(1), getRequestedFor(urlEqualTo("/search/searchbox/v1/category/wrong_category?access_token=mock_token&limit=25&proximity=17.3,51.3")));
    }


}