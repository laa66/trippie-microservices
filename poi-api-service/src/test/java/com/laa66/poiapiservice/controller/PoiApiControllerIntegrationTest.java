package com.laa66.poiapiservice.controller;

import com.laa66.poiapiservice.domain.PoiApiResponse;
import com.laa66.poiapiservice.domain.TripPoint;
import com.laa66.poiapiservice.service.PoiApiService;
import com.laa66.poiapiservice.service.impl.PoiMapboxService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@WebFluxTest(controllers = PoiApiController.class)
@Import(PoiMapboxService.class)
class PoiApiControllerIntegrationTest {

    @MockBean
    PoiApiService poiApiService;

    @Autowired
    WebTestClient webClient;

    @Test
    void shouldGetPoiApiResponseStatusOk() {
        Flux<TripPoint> tripPointFlux = Flux.just(mock(TripPoint.class), mock(TripPoint.class));
        when(poiApiService.getPoiCollection("gas_station", 20.354, 25.211))
                .thenReturn(tripPointFlux);

        webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/poi/gas_station")
                        .queryParam("longitude", 20.354)
                        .queryParam("latitude", 25.211)
                        .build())
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody()
                .jsonPath("$").isArray();

        verify(poiApiService, times(1))
                .getPoiCollection("gas_station", 20.354, 25.211);
    }

    @Test
    void shouldGetPoiApiResponseStatusBadRequest() {
        webClient.get()
                .uri("/poi/gas_station")
                .exchange()
                .expectStatus()
                .isBadRequest();

        verify(poiApiService, never())
                .getPoiCollection(anyString(), anyDouble(), anyDouble());
    }

}