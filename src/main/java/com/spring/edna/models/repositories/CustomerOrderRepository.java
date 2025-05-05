package com.spring.edna.models.repositories;

import com.spring.edna.models.entities.CustomerOrder;
import com.spring.edna.models.entities.Store;
import com.spring.edna.models.enums.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface CustomerOrderRepository extends JpaRepository<CustomerOrder, String>, JpaSpecificationExecutor<CustomerOrder> {

    long countByStoreIdAndStatusAndCreatedAtBetween(String storeId, OrderStatus status, LocalDateTime start, LocalDateTime end);

    @Query("SELECT SUM(clothe.priceInCents) FROM CustomerOrder co " +
            "JOIN co.clothe c " +
            "WHERE co.store = :store " +
            "AND co.status = 'COMPLETED' " +
            "AND co.createdAt BETWEEN :start AND :end")
    Integer getRevenueInPeriod(@Param("store") Store store,
                               @Param("start") LocalDateTime start,
                               @Param("end") LocalDateTime end);

    boolean existsByCustomerIdAndStoreId(String customerId, String storeId);

    @Query("""
                SELECT COUNT(DISTINCT co.customer.id)
                FROM CustomerOrder co
                WHERE co.store.id = :storeId
                AND co.createdAt BETWEEN :start AND :end
                AND co.isFirstOrder = true
            """)
    long countNewCustomersByStoreIdAndDateRange(
            @Param("storeId") String storeId,
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end);

}
