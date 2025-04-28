package com.spring.edna.services;

import com.spring.edna.google.GoogleDistanceMatrix;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LocationService {

    @Autowired
    private GoogleDistanceMatrix googleDistanceMatrix;

    public String calculateDistance(String from, String to) {
        String distance = googleDistanceMatrix.getDistance(from, to);

        return distance;
    }


}
