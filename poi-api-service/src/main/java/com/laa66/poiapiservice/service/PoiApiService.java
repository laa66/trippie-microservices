package com.laa66.poiapiservice.service;

import com.laa66.poiapiservice.domain.PoiApiResponse;

public interface PoiApiService {
    PoiApiResponse getPoiCollection(String category, double longitude, double latitude);
}