package com.spring.edna.models.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ClotheRankingDTO {
    private String clotheId;
    private String name;
    private String imageUrl;
    private Integer priceInCents;
    private Long savedCount;
    private boolean savedByCurrentUser;
}
