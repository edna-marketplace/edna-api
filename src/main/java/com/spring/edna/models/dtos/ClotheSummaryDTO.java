package com.spring.edna.models.dtos;

import com.spring.edna.models.enums.ClotheBrand;
import com.spring.edna.models.enums.ClotheSize;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ClotheSummaryDTO {

    private String clotheId;
    private String imagesURL;
    private String name;
    private String storeImageURL;
    private Integer priceInCents;
    private ClotheSize size;
    private ClotheBrand brand;
}
