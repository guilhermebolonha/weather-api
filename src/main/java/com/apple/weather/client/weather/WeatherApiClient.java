package com.apple.weather.client.weather;

import com.apple.weather.model.weather.WeatherApiResponse;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

@HttpExchange
public interface WeatherApiClient {

    @GetExchange("/v1/forecast")
    WeatherApiResponse getForecast(
            @RequestParam("latitude") double latitude,
            @RequestParam("longitude") double longitude,
            @RequestParam("current_weather") boolean currentWeather,
            @RequestParam("daily") String daily,
            @RequestParam("timezone") String timezone
    );
}

