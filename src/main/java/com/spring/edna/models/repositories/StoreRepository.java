package com.spring.edna.models.repositories;

import com.spring.edna.models.entities.Store;
import com.spring.edna.models.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StoreRepository extends JpaRepository<Store, String>, JpaSpecificationExecutor<Store> {

    Optional<Store> findByEmail(String email);
}
