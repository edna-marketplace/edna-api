package com.spring.edna.models.selectors;

import com.spring.edna.models.entities.ClotheOrder;
import com.spring.edna.models.enums.OrderStatus;
import jakarta.persistence.criteria.*;
import lombok.Data;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class ClotheOrderSelector extends BaseSelector implements Specification<ClotheOrder> {

    private String customerName;
    private OrderStatus status;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String clotheName;
    private Integer price;
    private String customerId;
    private String clotheId;

    @Override
    public Predicate toPredicate(Root<ClotheOrder> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        List<Predicate> predicates = new ArrayList<>();

        if (this.getCustomerName() != null && !this.getCustomerName().trim().isEmpty()) {
            predicates.add(cb.like(root.get("customer").get("name"), "%%" + this.getCustomerName() + "%%"));
        }

        if (this.getStatus() != null) {
            predicates.add(cb.equal(root.get("status"), this.getStatus()));
        }

        applyDateRangeFilter(root, cb, predicates, this.getStartDate(), this.getEndDate(), "createdAt");

        if (this.getClotheName() != null && !this.getClotheName().trim().isEmpty()) {
            predicates.add(cb.like(root.get("clothe").get("name"), "%%" + this.getClotheName() + "%%"));
        }

        if (this.getPrice() != null) {
            predicates.add(cb.equal(root.get("clothe").get("priceInCents"), this.getPrice()));
        }

        if (this.getCustomerId() != null) {
            predicates.add(cb.equal(root.get("customer").get("id"), this.getCustomerId()));
        }

        if (this.getClotheId() != null) {
            predicates.add(cb.equal(root.get("clothe").get("id"), this.getClotheId()));
        }

        return cb.and(predicates.toArray(new Predicate[0]));
    }
}
