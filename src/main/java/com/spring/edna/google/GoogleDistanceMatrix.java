package com.spring.edna.google;

import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

public class GoogleDistanceMatrix {

    private final String apiKey;
    private final RestTemplate restTemplate;
    private final String baseUrl = "https://maps.googleapis.com/maps/api/distancematrix/json";

    public GoogleDistanceMatrix(String apiKey) {
        this.apiKey = apiKey;
        this.restTemplate = new RestTemplate();
    }

    public String getDistance(String origin, String destination) {

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(baseUrl)
                .queryParam("origins", origin)
                .queryParam("destinations", destination)
                .queryParam("key", apiKey);

        DistanceMatrixResponse distanceMatrixResponse = restTemplate.getForObject(builder.toUriString(), DistanceMatrixResponse.class);

        return distanceMatrixResponse.getRows()[0].getElements()[0].getDistance().getText();
    }
}
