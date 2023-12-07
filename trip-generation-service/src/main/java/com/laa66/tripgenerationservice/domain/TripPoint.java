package com.laa66.tripgenerationservice.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

public record TripPoint(double longitude, double latitude, String name, @JsonProperty("full_address") String fullAddress) {

}
