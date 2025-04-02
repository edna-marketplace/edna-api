package com.spring.edna.models.repositories;

import com.spring.edna.models.entities.CustomerOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.UUID;

@Repository
public interface CustomerOrderRepository extends JpaRepository<CustomerOrder, String>, JpaSpecificationExecutor<CustomerOrder> {

    long countByStoreIdAndCreatedAtBetween(String storeId, LocalDateTime start, LocalDateTime end);

}
