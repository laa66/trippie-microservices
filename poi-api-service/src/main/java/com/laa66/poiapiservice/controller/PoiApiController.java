package com.laa66.poiapiservice.controller;

import com.laa66.poiapiservice.domain.PoiApiResponse;
import com.laa66.poiapiservice.service.PoiApiService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/poi")
@AllArgsConstructor
public class PoiApiController {

    private final PoiApiService poiApiService;

    @GetMapping("/{category}")
    public Mono<PoiApiResponse> getPoiApiResponse(@PathVariable String category,
                                                  @RequestParam double longitude,
                                                  @RequestParam double latitude) {
        return poiApiService.getPoiCollection(category, longitude, latitude);
    }

}
