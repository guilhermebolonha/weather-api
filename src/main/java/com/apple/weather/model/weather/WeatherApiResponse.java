package com.apple.weather.model.weather;

import java.util.List;

public record WeatherApiResponse(
        CurrentWeather current_weather,
        Daily daily
) {
    public record CurrentWeather(double temperature) {}
    public record Daily(
            List<Double> temperature_2m_min,
            List<Double> temperature_2m_max
    ) {}
}
