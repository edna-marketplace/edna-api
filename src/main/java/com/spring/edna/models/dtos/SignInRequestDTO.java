package com.spring.edna.models.dtos;

import lombok.Data;

@Data
public class SignInRequestDTO {
    private String email;
    private String password;
}
