package com.apple.weather.controller;

import com.apple.weather.model.WeatherResponse;
import com.apple.weather.service.WeatherService;
import org.springframework.cache.CacheManager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/weather")
public class WeatherController {

    private final WeatherService weatherService;
    private final CacheManager cacheManager;

    public WeatherController(
            WeatherService weatherService,
            CacheManager cacheManager) {
        this.weatherService = weatherService;
        this.cacheManager = cacheManager;
    }

    @GetMapping("/{zipCode}")
    public WeatherResponse getWeather(@PathVariable String zipCode) {

        boolean fromCache =
                cacheManager.getCache("weather").get(zipCode) != null;

        WeatherResponse response = weatherService.getWeather(zipCode);

        return new WeatherResponse(
                response.zipCode(),
                response.currentTemperature(),
                response.minTemperature(),
                response.maxTemperature(),
                fromCache
        );
    }
}

