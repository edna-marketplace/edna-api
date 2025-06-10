package com.spring.edna.models.dtos;

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
    private String imageURL;
    private String storeImageURL;
    private String storeName;
}
