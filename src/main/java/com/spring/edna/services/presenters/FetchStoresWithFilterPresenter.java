package com.spring.edna.services.presenters;

import com.spring.edna.models.dtos.PaginationMetaDTO;
import com.spring.edna.models.dtos.StoreSummaryDTO;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class FetchStoresWithFilterPresenter {

    private List<StoreSummaryDTO> stores;
    private PaginationMetaDTO meta;
}