package com.apple.weather.exception;

public record ErrorResponse(
        String code,
        String message
) {}
