package com.example.gestionassuree.repository;

import com.example.gestionassuree.entity.Voiture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VoitureRepo extends JpaRepository<Voiture,Long> {
}
