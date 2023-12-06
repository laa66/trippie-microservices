package com.laa66.poiapiservice.service.impl;

import com.laa66.poiapiservice.domain.PoiApiResponse;
import com.laa66.poiapiservice.domain.TripPoint;
import com.laa66.poiapiservice.service.PoiApiService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@AllArgsConstructor
public class PoiMapboxService implements PoiApiService {

    private final static String SEARCH_POI = "/search/searchbox/v1/category/{category}";

    private final WebClient webClient;

    @Override
    public Flux<TripPoint> getPoiCollection(String category, double longitude, double latitude) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder.path(SEARCH_POI)
                        .queryParam("proximity", "{longitude},{latitude}")
                        .queryParam("poi_category_exclusions", "services")
                        .build(category, longitude, latitude))
                .retrieve()
                .onStatus(httpStatusCode -> HttpStatus.NOT_FOUND == httpStatusCode, clientResponse -> Mono.empty())
                .bodyToMono(PoiApiResponse.class)
                .flatMapMany(poiApiResponse -> Flux.fromStream(() -> poiApiResponse
                        .features()
                        .stream()
                        .map(feature -> new TripPoint(
                                feature.geometry().coordinates()[0],
                                feature.geometry().coordinates()[1],
                                feature.properties().name(),
                                feature.properties().fullAddress()
                        ))));
    }
}
