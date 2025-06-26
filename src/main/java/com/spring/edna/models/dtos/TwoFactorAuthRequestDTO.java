package com.spring.edna.models.dtos;

import lombok.Data;

@Data
public class TwoFactorAuthRequestDTO {
    private String email;
    private String password;
    private String otp;
}
