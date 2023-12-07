package com.laa66.tripgenerationservice.service.impl;

import com.laa66.tripgenerationservice.domain.TripPoint;
import com.laa66.tripgenerationservice.service.TripGenerationService;
import lombok.AllArgsConstructor;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.util.Collection;

@AllArgsConstructor
public class TripGenerationServiceImpl implements TripGenerationService {

    private final static String GET_TRIP = "/poi/{category}";

    private final WebClient webClient;

    @Override
    public Collection<TripPoint> createTrip(int size,
                                            double longitude,
                                            double latitude,
                                            String... categories) {
        return null;
    }

    @Override
    public Collection<TripPoint> optimizeTrip(Collection<TripPoint> tripRoute) {
        return null;
    }

    @Override
    public Flux<TripPoint> getSingleCategoryTrip(double longitude,
                                                   double latitude,
                                                   String category) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder.path(GET_TRIP)
                        .queryParam("longitude", "{longitude}")
                        .queryParam("latitude", "{latitude}")
                        .build(category, longitude, latitude))
                .retrieve()
                .bodyToFlux(TripPoint.class);
    }

}
