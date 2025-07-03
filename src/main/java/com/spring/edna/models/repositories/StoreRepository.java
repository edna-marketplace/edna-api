package com.spring.edna.models.repositories;

import com.spring.edna.models.entities.Store;
import com.spring.edna.models.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StoreRepository extends JpaRepository<Store, String>, JpaSpecificationExecutor<Store> {

    Optional<Store> findByEmail(String email);

    Optional<Store> findByStripeAccountId(String stripeAccountId);

    List<Store> findByNameOrEmailOrCnpjOrPhone(String name, String email, String cnpj, String phone);

    List<Store> findByEmailOrPhone(String email, String phone);
}
