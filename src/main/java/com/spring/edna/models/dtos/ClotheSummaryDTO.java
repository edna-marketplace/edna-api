package com.spring.edna.models.dtos;

import com.spring.edna.models.enums.ClotheBrand;
import com.spring.edna.models.enums.ClotheSize;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Optional;

@Data
@AllArgsConstructor
public class ClotheSummaryDTO {

    private String id;
    private String name;
    private Integer priceInCents;
    private ClotheSize size;
    private ClotheBrand brand;
    private String imageURL;
    private String storeImageURL;

}
