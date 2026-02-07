package com.apple.weather.controller;

import com.apple.weather.model.WeatherResponse;
import com.apple.weather.service.WeatherService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import org.springframework.cache.Cache;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.cache.CacheManager;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(WeatherController.class)
class WeatherControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private WeatherService weatherService;

    @MockitoBean
    private CacheManager cacheManager;

    @Test
    void shouldReturnWeatherResponse() throws Exception {
        String zipCode = "90210";

        WeatherResponse response =
                new WeatherResponse(zipCode, 25.0, 18.0, 30.0, false);

        when(weatherService.getWeather(zipCode))
                .thenReturn(response);

        Cache cache = mock(Cache.class);
        when(cacheManager.getCache("weather")).thenReturn(cache);
        when(cache.get(zipCode)).thenReturn(null);

        mockMvc.perform(get("/weather/{zipCode}", zipCode))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.zipCode").value(zipCode))
                .andExpect(jsonPath("$.currentTemperature").value(25.0));
    }
}
