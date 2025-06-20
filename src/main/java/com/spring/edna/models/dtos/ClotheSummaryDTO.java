package com.spring.edna.models.dtos;

import com.spring.edna.models.enums.ClotheBrand;
import com.spring.edna.models.enums.ClotheSize;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ClotheSummaryDTO {

    private String id;
    private String name;
    private Integer priceInCents;
    private ClotheBrand brand;
    private String brandOther;
    private ClotheSize size;
    private String sizeOther;
    private boolean isSaved;
    private String imageURL;
    private String storeName;
    private String storeImageURL;
}
