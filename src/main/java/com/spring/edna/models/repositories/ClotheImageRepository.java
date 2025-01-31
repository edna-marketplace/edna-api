package com.spring.edna.models.repositories;

import com.spring.edna.models.entities.ClotheImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClotheImageRepository extends JpaRepository<ClotheImage, String> {

}
