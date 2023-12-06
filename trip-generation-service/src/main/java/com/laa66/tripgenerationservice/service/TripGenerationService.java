package com.laa66.tripgenerationservice.service;

public interface TripGenerationService {

    // request n times poi-api-service with chosen category
    void createTrip();

    // find and optimize the closest points in trip data collection
    void optimizeTrip();

}
