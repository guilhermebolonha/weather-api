package com.apple.weather.service;

import com.apple.weather.client.weather.WeatherApiClient;
import com.apple.weather.exception.WeatherServiceUnavailableException;
import com.apple.weather.model.Coordinates;
import com.apple.weather.model.WeatherResponse;
import com.apple.weather.model.weather.WeatherApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class WeatherService {

    private final WeatherApiClient weatherApiClient;
    private final GeocodingService geocodingService;

    public WeatherService(
            WeatherApiClient weatherApiClient,
            GeocodingService geocodingService) {
        this.weatherApiClient = weatherApiClient;
        this.geocodingService = geocodingService;
    }

    @Cacheable(value = "weather", key = "#zipCode")
    public WeatherResponse getWeather(String zipCode) {

        log.debug("Fetching weather for zip code: {}", zipCode);

        Coordinates coordinates = geocodingService.getCoordinates(zipCode);

        try {
            WeatherApiResponse apiResponse =
                    weatherApiClient.getForecast(
                            coordinates.latitude(),
                            coordinates.longitude(),
                            true,
                            "temperature_2m_max,temperature_2m_min",
                            "auto"
                    );

            return new WeatherResponse(
                    zipCode,
                    apiResponse.current_weather().temperature(),
                    apiResponse.daily().temperature_2m_min().getFirst(),
                    apiResponse.daily().temperature_2m_max().getFirst(),
                    false
            );

        } catch (Exception ex) {
            throw new WeatherServiceUnavailableException();
        }
    }
}
