package com.example.gestionassuree.repository;

import com.example.gestionassuree.entity.Assuree;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AssureeRepo extends JpaRepository<Assuree,Long> {

    Optional<Assuree> findByUsername(String username);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);
}
