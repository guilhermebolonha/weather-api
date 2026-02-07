package com.apple.weather.exception;

public class ZipCodeNotFoundException extends RuntimeException {

    public ZipCodeNotFoundException(String zipCode) {
        super("Zip code not found: " + zipCode);
    }
}
