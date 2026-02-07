package com.apple.weather.exception;

public class WeatherServiceUnavailableException extends RuntimeException {

    public WeatherServiceUnavailableException() {
        super("Weather service is currently unavailable");
    }
}
