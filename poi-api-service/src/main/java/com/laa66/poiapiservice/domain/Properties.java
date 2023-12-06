package com.laa66.poiapiservice.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Properties(String name,
                         @JsonProperty("full_address")
                         String fullAddress,
                         @JsonProperty("poi_category")
                         String[] poiCategory) {
}
