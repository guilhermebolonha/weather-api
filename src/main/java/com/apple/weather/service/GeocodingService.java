package com.apple.weather.service;

import com.apple.weather.client.geocoding.GeocodingApiClient;
import com.apple.weather.exception.ZipCodeNotFoundException;
import com.apple.weather.model.Coordinates;
import org.springframework.stereotype.Service;

@Service
public class GeocodingService {

    private final GeocodingApiClient client;

    public GeocodingService(GeocodingApiClient client) {
        this.client = client;
    }

    public Coordinates getCoordinates(String zipCode) {
        return client.search(zipCode, "json").stream()
                .findFirst()
                .map(r -> new Coordinates(r.lat(), r.lon()))
                .orElseThrow(() -> new ZipCodeNotFoundException(zipCode));
    }
}

