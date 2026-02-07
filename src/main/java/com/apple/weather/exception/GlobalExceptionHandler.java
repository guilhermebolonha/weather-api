package com.apple.weather.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(ZipCodeNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleZipCodeNotFound(
            ZipCodeNotFoundException ex) {

        log.debug(ex.getMessage());

        ErrorResponse error = new ErrorResponse(
                "ZIP_CODE_NOT_FOUND",
                ex.getMessage()
        );

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(WeatherServiceUnavailableException.class)
    public ResponseEntity<ErrorResponse> handleWeatherUnavailable(
            WeatherServiceUnavailableException ex) {

        log.debug(ex.getMessage());

        return ResponseEntity
                .status(HttpStatus.SERVICE_UNAVAILABLE)
                .body(new ErrorResponse(
                        "WEATHER_SERVICE_UNAVAILABLE",
                        ex.getMessage()
                ));
    }

}
