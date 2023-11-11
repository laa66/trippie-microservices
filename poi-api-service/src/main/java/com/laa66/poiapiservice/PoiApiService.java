package com.laa66.poiapiservice;

public interface PoiApiService {

    String SEARCH_POI = "https://api.mapbox.com/search/searchbox/v1/category/{category_name}?access_token={access_token}&limit=25&proximity={longitude},{latitude}";

}
