package com.laa66.poiapiservice.service;

import com.laa66.poiapiservice.domain.PoiApiResponse;
import reactor.core.publisher.Mono;

public interface PoiApiService {
    Mono<PoiApiResponse> getPoiCollection(String category, double longitude, double latitude);
}