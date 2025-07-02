package com.spring.edna.controllers;

import com.spring.edna.auth.AuthService;
import com.spring.edna.exception.EdnaException;
import com.spring.edna.models.dtos.ClotheRankingDTO;
import com.spring.edna.models.entities.User;
import com.spring.edna.services.GetThreeMostSavedClothes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/customer")
public class GetThreeMostSavedClothesController {

    @Autowired
    private AuthService authService;

    @Autowired
    private GetThreeMostSavedClothes getThreeMostSavedClothes;

    @GetMapping("/clothes/ranking")
    public List<ClotheRankingDTO> getTopClothes() throws EdnaException {
        User subject = authService.getAuthenticatedUser();
        return getThreeMostSavedClothes.execute(subject.getId());
    }
}
