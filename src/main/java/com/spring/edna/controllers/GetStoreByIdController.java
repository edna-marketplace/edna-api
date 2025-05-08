package com.spring.edna.controllers;

import com.spring.edna.auth.AuthService;
import com.spring.edna.exception.EdnaException;
import com.spring.edna.models.dtos.CoordinatesDTO;
import com.spring.edna.models.entities.User;
import com.spring.edna.services.GetStoreById;
import com.spring.edna.services.GetStoreById.GetStoreByIdResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/stores")
public class GetStoreByIdController {

    @Autowired
    private GetStoreById getStoreById;

    @Autowired
    private AuthService authService;

    @GetMapping("/{storeId}")
    public GetStoreByIdResponse handle(
            @PathVariable String storeId,
            @RequestParam(required = false) String latitude,
            @RequestParam(required = false) String longitude
    ) throws EdnaException {
        User subject = authService.getAuthenticatedUser();

        CoordinatesDTO customerCoordinates = (latitude != null && longitude != null)
                ? new CoordinatesDTO(latitude, longitude)
                : null;

        return getStoreById.execute(storeId, subject.getId(), customerCoordinates);
    }
}
