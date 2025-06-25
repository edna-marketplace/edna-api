package com.spring.edna.models.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UpdatePasswordRequestDTO {
    private String oldPassword;
    private String newPassword;
}
