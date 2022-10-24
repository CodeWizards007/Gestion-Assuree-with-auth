package com.example.gestionassuree.entity;

import com.example.gestionassuree.model.Abonnement;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Voiture {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long immatriculation;
    private Integer nbreChauveaux;
    private Date miseEnCirculation;
    private Double valeur;
    private Long idAbonnement;
    @Transient
    private Abonnement abonnement;

}
