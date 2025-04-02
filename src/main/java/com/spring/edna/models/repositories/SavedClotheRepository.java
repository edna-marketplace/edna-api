package com.spring.edna.models.repositories;

import com.spring.edna.models.entities.Clothe;
import com.spring.edna.models.entities.SavedClothe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SavedClotheRepository extends JpaRepository<SavedClothe, String> {
    Optional<SavedClothe> findByClothe(Clothe clothe);
}
