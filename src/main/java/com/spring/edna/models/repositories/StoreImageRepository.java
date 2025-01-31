package com.spring.edna.models.repositories;

import com.spring.edna.models.entities.StoreImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StoreImageRepository extends JpaRepository<StoreImage, String> {

}
