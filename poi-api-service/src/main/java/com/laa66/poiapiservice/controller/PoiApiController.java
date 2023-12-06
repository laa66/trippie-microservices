package com.laa66.poiapiservice.controller;

import com.laa66.poiapiservice.domain.TripPoint;
import com.laa66.poiapiservice.service.PoiApiService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/poi")
@AllArgsConstructor
public class PoiApiController {

    private final PoiApiService poiApiService;

    @GetMapping("/{category}")
    public Flux<TripPoint> getPoiApiResponse(@PathVariable String category,
                                             @RequestParam double longitude,
                                             @RequestParam double latitude) {
        return poiApiService.getPoiCollection(category, longitude, latitude);
    }

}
