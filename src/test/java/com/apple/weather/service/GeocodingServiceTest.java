package com.apple.weather.service;

import com.apple.weather.client.geocoding.GeocodingApiClient;
import com.apple.weather.exception.ZipCodeNotFoundException;
import com.apple.weather.model.Coordinates;
import com.apple.weather.model.geocoding.GeocodingApiResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GeocodingServiceTest {

    @Mock
    private GeocodingApiClient geocodingApiClient;

    @InjectMocks
    private GeocodingService geocodingService;

    @Test
    void shouldReturnCoordinatesWhenZipCodeExists() {
        when(geocodingApiClient.search("90210", "json"))
                .thenReturn(List.of(new GeocodingApiResponse(34.0, -118.0)));

        Coordinates coordinates = geocodingService.getCoordinates("90210");

        assertEquals(34.0, coordinates.latitude());
        assertEquals(-118.0, coordinates.longitude());
    }

    @Test
    void shouldThrowExceptionWhenZipCodeIsNotFound() {
        when(geocodingApiClient.search(anyString(), anyString()))
                .thenReturn(List.of());

        assertThrows(
                ZipCodeNotFoundException.class,
                () -> geocodingService.getCoordinates("00000")
        );
    }
}
