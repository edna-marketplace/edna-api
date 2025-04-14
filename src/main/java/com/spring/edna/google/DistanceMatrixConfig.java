package com.spring.edna.google;

import org.springframework.beans.factory.annotation.Value;

public class DistanceMatrixConfig {

    @Value("${google.access.key}")
    private String accessKey;


}
