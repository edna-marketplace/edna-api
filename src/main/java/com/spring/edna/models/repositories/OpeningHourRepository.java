package com.spring.edna.models.repositories;

import com.spring.edna.models.entities.OpeningHour;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OpeningHourRepository extends JpaRepository<OpeningHour, String> {
    List<OpeningHour> findByStoreId(String storeId);
}
