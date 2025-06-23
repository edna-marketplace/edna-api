package com.spring.edna.models.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RankingClotheDTO {
    private String id;
    private String name;
    private Integer priceInCents;
    private boolean isSaved;
    private Integer numberOfSaves;
    private String imageURL;
}
