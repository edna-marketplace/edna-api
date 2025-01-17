package com.spring.edna.models.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class CustomerDetailsDTO {
    private String id;
    private String name;
    private String email;
    private String phone;
    private String cpf;
}