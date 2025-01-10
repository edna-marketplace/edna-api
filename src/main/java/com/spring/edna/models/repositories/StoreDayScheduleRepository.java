package com.spring.edna.models.repositories;

import com.spring.edna.models.entities.StoreDaySchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StoreDayScheduleRepository extends JpaRepository<StoreDaySchedule, String> {
    List<StoreDaySchedule> findByStoreId(String storeId);
}
