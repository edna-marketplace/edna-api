package com.spring.edna.models.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PaginationMetaDTO {
    private int pageIndex;
    private int perPage;
    private int totalCount;
}
