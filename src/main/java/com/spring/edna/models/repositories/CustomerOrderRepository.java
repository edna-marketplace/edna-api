package com.spring.edna.models.repositories;

import com.spring.edna.models.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerOrderRepository extends JpaRepository<Order, String>, JpaSpecificationExecutor<Order> {
}
