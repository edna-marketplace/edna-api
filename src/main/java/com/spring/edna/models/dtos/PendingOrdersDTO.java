package com.spring.edna.models.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class PendingOrdersDTO {

    private String clotheName;
    private LocalDateTime createdAt;

}
