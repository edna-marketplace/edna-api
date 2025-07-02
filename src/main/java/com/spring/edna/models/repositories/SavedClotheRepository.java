package com.spring.edna.models.repositories;

import com.spring.edna.models.entities.Clothe;
import com.spring.edna.models.entities.SavedClothe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface SavedClotheRepository extends JpaRepository<SavedClothe, String> {
    Optional<SavedClothe> findByClothe(Clothe clothe);

    @Query("""
            SELECT sc.clothe.id, sc.clothe.name, sc.clothe.priceInCents, COUNT(sc.customer.id)
            FROM SavedClothe sc
            WHERE sc.createdAt BETWEEN :startOfMonth AND :endOfMonth
            GROUP BY sc.clothe.id, sc.clothe.name, sc.clothe.priceInCents
            ORDER BY COUNT(sc.customer.id) DESC
            """)
    List<Object[]> findTopSavedClothesInMonth(LocalDateTime startOfMonth, LocalDateTime endOfMonth);

    boolean existsByCustomerIdAndClotheId(String customerId, String clotheId);
}
