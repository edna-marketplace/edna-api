package com.spring.edna.services.presenters;

import com.spring.edna.models.dtos.ClotheSummaryDTO;
import com.spring.edna.models.dtos.PaginationMetaDTO;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class FetchClothesWithFilterPresenter {

    private List<ClotheSummaryDTO> clothes;
    private PaginationMetaDTO meta;
}
