package com.spring.edna.models.dtos;

import com.spring.edna.models.enums.ClotheBrand;
import com.spring.edna.models.enums.ClotheSize;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FeedClotheDTO {

    private String id;
    private String name;
    private Integer priceInCents;
    private ClotheSize size;
    private String sizeOther;
    private ClotheBrand brand;
    private String brandOther;
    private String imageURL;
    private String storeId;
    private String storeName;
    private String storeImageURL;
}
