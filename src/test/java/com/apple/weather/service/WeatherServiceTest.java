package com.apple.weather.service;

import com.apple.weather.client.weather.WeatherApiClient;
import com.apple.weather.exception.WeatherServiceUnavailableException;
import com.apple.weather.model.Coordinates;
import com.apple.weather.model.WeatherResponse;
import com.apple.weather.model.weather.WeatherApiResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class WeatherServiceTest {

    @Mock
    private WeatherApiClient weatherApiClient;

    @Mock
    private GeocodingService geocodingService;

    @InjectMocks
    private WeatherService weatherService;

    @Test
    void shouldReturnWeatherWhenZipCodeIsValid() {
        String zipCode = "90210";
        Coordinates coordinates = new Coordinates(34.09, -118.40);

        when(geocodingService.getCoordinates(zipCode))
                .thenReturn(coordinates);

        WeatherApiResponse apiResponse =
                new WeatherApiResponse(
                        new WeatherApiResponse.CurrentWeather(25.0),
                        new WeatherApiResponse.Daily(
                                List.of(18.0),
                                List.of(30.0)
                        )
                );

        when(weatherApiClient.getForecast(
                anyDouble(),
                anyDouble(),
                eq(true),
                anyString(),
                anyString()
        )).thenReturn(apiResponse);

        WeatherResponse response = weatherService.getWeather(zipCode);

        assertEquals(zipCode, response.zipCode());
        assertEquals(25.0, response.currentTemperature());
        assertEquals(18.0, response.minTemperature());
        assertEquals(30.0, response.maxTemperature());
    }

    @Test
    void shouldThrowExceptionWhenWeatherApiFails() {
        when(geocodingService.getCoordinates(anyString()))
                .thenReturn(new Coordinates(10.0, 10.0));

        when(weatherApiClient.getForecast(
                anyDouble(),
                anyDouble(),
                anyBoolean(),
                anyString(),
                anyString()
        )).thenThrow(new RuntimeException("API down"));

        assertThrows(
                WeatherServiceUnavailableException.class,
                () -> weatherService.getWeather("90210")
        );
    }
}

