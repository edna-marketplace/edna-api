package com.spring.edna.google;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GoogleDistanceMatrixConfig {

    @Value("${google.api.key}")
    private String apiKey;

    @Bean
    public GoogleDistanceMatrix distanceMatrixApi() {
        return new GoogleDistanceMatrix(apiKey);
    }
}
