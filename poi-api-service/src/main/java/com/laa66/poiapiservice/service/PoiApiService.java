package com.laa66.poiapiservice.service;

import com.laa66.poiapiservice.domain.TripPoint;
import reactor.core.publisher.Flux;

public interface PoiApiService {
    Flux<TripPoint> getPoiCollection(String category, double longitude, double latitude);
}