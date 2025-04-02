package com.spring.edna.models.repositories;

import com.spring.edna.models.entities.Clothe;
import com.spring.edna.models.entities.CustomerOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerOrderRepository extends JpaRepository<CustomerOrder, String>, JpaSpecificationExecutor<CustomerOrder> {

    Optional<CustomerOrder> findByClotheId(String clotheId);
}
