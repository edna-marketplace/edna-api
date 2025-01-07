package com.spring.edna.models.repositories;

import com.spring.edna.models.entities.Store;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.br.CNPJ;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StoreRepository extends JpaRepository<Store, String>, JpaSpecificationExecutor<Store> {

//    Optional<Store> findByName(String name);
//
//    Optional<Store> findByCnpj(String cnpj);
//
//    Optional<Store> findByEmail(String email);
}
