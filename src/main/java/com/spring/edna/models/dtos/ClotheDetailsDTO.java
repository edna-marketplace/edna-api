package com.spring.edna.models.dtos;

import com.spring.edna.models.entities.ClotheImage;
import com.spring.edna.models.enums.ClotheBrand;
import com.spring.edna.models.enums.ClotheCategory;
import com.spring.edna.models.enums.ClotheGender;
import com.spring.edna.models.enums.ClotheSize;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ClotheDetailsDTO {

    private String clotheId;
    private String name;
    private Integer priceInCents;
    private String description;
    private ClotheSize size;
    private ClotheGender gender;
    private String fabric;
    private String color;
    private Integer lengthInCm;
    private Integer widthInCm;
    private ClotheBrand brand;
    private ClotheCategory category;
    private String storeName;
    private List<ClotheImageDTO> images;
}
