package com.spring.edna.models.selectors;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.spring.edna.models.entities.Store;
import com.spring.edna.models.enums.TargetCustomer;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.Data;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

@Data
public class StoreSelector extends BaseSelector implements Specification<Store> {

    private String storeName;
    private TargetCustomer targetCustomer;

    @JsonProperty("isFavorite")
    private boolean isFavorite;

    @Override
    public Predicate toPredicate(Root<Store> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        List<Predicate> predicates = new ArrayList<>();

        if(this.getStoreName() != null && !this.getStoreName().trim().isEmpty()) {
            predicates.add(cb.like(root.get("storeName"), "%" + this.getStoreName() + "%"));
        }

        if(this.getTargetCustomer() != null) {
            predicates.add(cb.equal(root.get("targetCustomer"), this.getTargetCustomer()));
        }

        return cb.and(predicates.toArray(new Predicate[0]));
    }
}
