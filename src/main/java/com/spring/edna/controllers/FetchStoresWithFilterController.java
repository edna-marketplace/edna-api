package com.spring.edna.controllers;

import com.spring.edna.auth.AuthService;
import com.spring.edna.exception.EdnaException;
import com.spring.edna.models.dtos.CoordinatesDTO;
import com.spring.edna.models.entities.User;
import com.spring.edna.models.selectors.StoreSelector;
import com.spring.edna.services.FetchStoresWithFilter;
import com.spring.edna.services.FetchStoresWithFilter.FetchStoresWithFilterResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/stores")
public class FetchStoresWithFilterController {

    @Autowired
    private FetchStoresWithFilter fetchStoresWithFilter;

    @Autowired
    private AuthService authService;

    @PostMapping("/filter")
    public FetchStoresWithFilterResponse handle(
            @RequestBody StoreSelector selector,
            @RequestParam(required = false) String latitude,
            @RequestParam(required = false) String longitude
    ) throws EdnaException {
        User subject = authService.getAuthenticatedUser();

        CoordinatesDTO customerCoordinates = (latitude != null && longitude != null)
                ? new CoordinatesDTO(latitude, longitude)
                : null;

        return fetchStoresWithFilter.execute(selector, subject.getId(), customerCoordinates);
    }
}

