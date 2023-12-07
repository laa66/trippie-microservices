package com.laa66.tripgenerationservice.service;

import com.laa66.tripgenerationservice.domain.TripPoint;
import reactor.core.publisher.Flux;

import java.util.Collection;

public interface TripGenerationService {

    // request n times poi-api-service with chosen category
    Collection<TripPoint> createTrip(int size,
                                     double longitude,
                                     double latitude,
                                     String... categories);

    // find and optimize the closest points in trip data collection
    Collection<TripPoint> optimizeTrip(Collection<TripPoint> tripRoute);

    // request poi-api-service for one category trip
    Flux<TripPoint> getSingleCategoryTrip(double longitude,
                                            double latitude,
                                            String category);

}
