package com.apple.weather.client.geocoding;

import com.apple.weather.model.geocoding.GeocodingApiResponse;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

import java.util.List;

@HttpExchange
public interface GeocodingApiClient {

    @GetExchange("/search")
    List<GeocodingApiResponse> search(
            @RequestParam("postalcode") String zipCode,
            @RequestParam("format") String format
    );
}
