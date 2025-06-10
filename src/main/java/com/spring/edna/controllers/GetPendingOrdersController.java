package com.spring.edna.controllers;

import com.spring.edna.auth.AuthService;
import com.spring.edna.exception.EdnaException;
import com.spring.edna.models.dtos.PendingOrdersDTO;
import com.spring.edna.models.entities.User;
import com.spring.edna.services.GetPendingOrders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/store/metrics")
public class GetPendingOrdersController {

    @Autowired
    private GetPendingOrders getPendingOrders;

    @Autowired
    private AuthService authService;


    @GetMapping(path = "/pending-orders")
    public ResponseEntity<List<PendingOrdersDTO>> handle() throws EdnaException {

        User subject = authService.getAuthenticatedUser();

        List<PendingOrdersDTO> pendingOrders = getPendingOrders.execute(subject.getId());

        return ResponseEntity.ok(pendingOrders);
    }
}
