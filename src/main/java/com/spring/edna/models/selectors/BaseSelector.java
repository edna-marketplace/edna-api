package com.spring.edna.models.selectors;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.Data;

@Data
public abstract class BaseSelector {

    private int page;
    private int limit;

    public BaseSelector() {
        this.limit = 10;
        this.page = 1;
    }

    public boolean hasPagination() {
        return this.limit > 0 && this.page > 0;
    }

    public static void applyDateRangeFilter(Root root,
                                            CriteriaBuilder cb, List<Predicate> predicates,
                                            LocalDateTime startDate, LocalDateTime endDate, String attributeName) {
        if (startDate != null && endDate != null) {
            predicates.add(cb.between(root.get(attributeName), startDate, endDate));
        } else if (startDate != null) {
            predicates.add(cb.greaterThanOrEqualTo(root.get(attributeName), startDate));
        } else if (endDate != null) {
            predicates.add(cb.lessThanOrEqualTo(root.get(attributeName), endDate));
        }
    }
}
