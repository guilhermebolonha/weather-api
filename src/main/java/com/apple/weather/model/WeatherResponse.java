package com.apple.weather.model;

public record WeatherResponse(
        String zipCode,
        double currentTemperature,
        double minTemperature,
        double maxTemperature,
        boolean fromCache
) {}
