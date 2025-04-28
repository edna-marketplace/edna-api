package com.spring.edna.controllers;

import com.spring.edna.services.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/location")
public class LocationController {

    @Autowired
    private LocationService locationService;

    @GetMapping
    public String handler(@RequestParam String origin, @RequestParam String destination) {
        return locationService.calculateDistance(origin, destination);
    }
}
