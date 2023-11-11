package com.laa66.poiapiservice.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record PoiApiResponse(String type, List<Feature> features) {
}
