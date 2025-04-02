package com.spring.edna.models.repositories;

import com.spring.edna.models.entities.CustomerOrder;
import com.spring.edna.models.entities.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface CustomerOrderRepository extends JpaRepository<CustomerOrder, String>, JpaSpecificationExecutor<CustomerOrder> {

    long countByStoreIdAndCreatedAtBetween(String storeId, LocalDateTime start, LocalDateTime end);

    @Query("SELECT SUM(clothe.priceInCents) FROM CustomerOrder o " +
            "JOIN o.clothe c " +
            "WHERE o.store = :store " +
            "AND o.status = 'COMPLETED' " +
            "AND o.createdAt BETWEEN :start AND :end")
    Integer getRevenueInPeriod(@Param("store") Store store,
                              @Param("start") LocalDateTime start,
                              @Param("end") LocalDateTime end);
}
