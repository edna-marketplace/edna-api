package com.spring.edna.models.repositories;

import com.spring.edna.models.entities.Clothe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ClotheRepository extends JpaRepository<Clothe, String>, JpaSpecificationExecutor<Clothe> {
}
