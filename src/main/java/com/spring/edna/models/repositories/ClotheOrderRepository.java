package com.spring.edna.models.repositories;

import com.spring.edna.models.dtos.RevenuePeriodDTO;
import com.spring.edna.models.entities.ClotheOrder;
import com.spring.edna.models.entities.Store;
import com.spring.edna.models.enums.OrderStatus;
import com.spring.edna.models.selectors.ClotheOrderSelector;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ClotheOrderRepository extends JpaRepository<ClotheOrder, String>, JpaSpecificationExecutor<ClotheOrder> {

    Optional<ClotheOrder> findByPaymentIntentId(String paymentIntentId);

    Optional<ClotheOrder> findByClotheId(String clotheId);

    long countByStoreIdAndStatusAndCreatedAtBetween(String storeId, OrderStatus status, LocalDateTime start, LocalDateTime end);

    @Query("SELECT SUM(clothe.priceInCents) FROM ClotheOrder co " +
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
                FROM ClotheOrder co
                WHERE co.store.id = :storeId
                AND co.createdAt BETWEEN :start AND :end
                AND co.status = 'PENDING'
                AND co.isFirstOrder = true
            """)
    long countNewCustomersByStoreIdAndDateRange(
            @Param("storeId") String storeId,
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end);

    @Query("""
                SELECT co
                FROM ClotheOrder co
                WHERE co.store = :store
                  AND co.status = 'COMPLETED'
                  AND co.createdAt BETWEEN :start AND :end
            """)
    List<ClotheOrder> findCompletedOrdersByStoreAndPeriod(
            @Param("store") Store store,
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end);

    @Query("SELECT c FROM ClotheOrder c WHERE " +
            "(:#{#selector.customerName} IS NULL OR UPPER(c.customer.name) LIKE UPPER(CONCAT('%', :#{#selector.customerName}, '%'))) AND " +
            "(:#{#selector.shouldApplyStatusFilter()} = false OR c.status = :#{#selector.orderStatus}) AND " +
            "(:#{#selector.startDate} IS NULL OR c.createdAt >= :#{#selector.startDate}) AND " +
            "(:#{#selector.endDate} IS NULL OR c.createdAt <= :#{#selector.endDate}) AND " +
            "(:#{#selector.clotheName} IS NULL OR UPPER(c.clothe.name) LIKE UPPER(CONCAT('%', :#{#selector.clotheName}, '%'))) AND " +
            "(:#{#selector.price} IS NULL OR c.clothe.priceInCents = :#{#selector.price}) AND " +
            "(:#{#selector.storeId} IS NULL OR c.store.id = :#{#selector.storeId}) AND " +
            "(:#{#selector.customerId} IS NULL OR c.customer.id = :#{#selector.customerId}) AND " +
            "(:#{#selector.clotheId} IS NULL OR c.clothe.id = :#{#selector.clotheId}) " +
            "ORDER BY CASE c.status " +
            "WHEN 'PENDING' THEN 1 " +
            "WHEN 'AWAITING_WITHDRAWAL' THEN 2 " +
            "WHEN 'COMPLETED' THEN 3 " +
            "WHEN 'CANCELED' THEN 4 " +
            "ELSE 5 END, c.createdAt DESC")
    Page<ClotheOrder> findAllWithStatusOrder(@Param("selector") ClotheOrderSelector selector, Pageable pageable);
}
