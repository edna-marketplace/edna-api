package com.spring.edna.models.repositories;

import com.spring.edna.models.entities.ClotheOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClotheOrderRepository extends JpaRepository<ClotheOrder, String>, JpaSpecificationExecutor<ClotheOrder> {

    Optional<ClotheOrder> findByClotheId(String clotheId);
}
