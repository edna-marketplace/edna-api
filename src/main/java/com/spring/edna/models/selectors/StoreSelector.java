package com.spring.edna.models.selectors;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.spring.edna.models.entities.Customer;
import com.spring.edna.models.entities.Store;
import com.spring.edna.models.enums.TargetCustomer;
import jakarta.persistence.criteria.*;
import lombok.Data;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Data
public class StoreSelector extends BaseSelector implements Specification<Store> {

    private String name;
    private TargetCustomer targetCustomer;

    @JsonProperty("isFavorite")
    private boolean isFavorite;

    private String customerId;

    @Override
    public Predicate toPredicate(Root<Store> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        List<Predicate> predicates = new ArrayList<>();

        predicates.add(cb.equal(root.get("deleted"), false));

        if(this.getName() != null && !this.getName().trim().isEmpty()) {
            predicates.add(cb.like(cb.lower(root.get("name")), "%" + this.getName() + "%"));
        }

        if(this.getTargetCustomer() != null && this.getTargetCustomer() != TargetCustomer.ALL) {
            predicates.add(cb.equal(root.get("targetCustomer"), this.getTargetCustomer()));
        }

        if (this.isFavorite()) {
            Subquery<String> favoriteSubquery = query.subquery(String.class);
            Root<Customer> customerRoot = favoriteSubquery.from(Customer.class);

            Join<Customer, Store> favoriteStoresJoin = customerRoot.join("favoriteStores");

            favoriteSubquery.select(customerRoot.get("id"))
                    .where(
                            cb.equal(customerRoot.get("id"), this.getCustomerId()),
                            cb.equal(favoriteStoresJoin, root)
                    );

            predicates.add(cb.exists(favoriteSubquery));
        }

        return cb.and(predicates.toArray(new Predicate[0]));
    }
}
