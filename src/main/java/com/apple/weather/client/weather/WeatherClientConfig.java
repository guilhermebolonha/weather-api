package com.apple.weather.client.weather;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
public class WeatherClientConfig {

    @Bean
    WeatherApiClient weatherApiClient(
            @Value("${clients.weather.base-url}") String baseUrl) {

        WebClient webClient = WebClient.builder()
                .baseUrl(baseUrl)
                .build();

        WebClientAdapter adapter = WebClientAdapter.create(webClient);

        HttpServiceProxyFactory factory = HttpServiceProxyFactory
                .builder()
                .exchangeAdapter(adapter)
                .build();

        return factory.createClient(WeatherApiClient.class);
    }
}



