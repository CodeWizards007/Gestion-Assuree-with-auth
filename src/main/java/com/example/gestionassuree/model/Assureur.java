package com.example.gestionassuree.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Assureur {
    private Long id;
    private String nom;
    private String prenom;
    private Long cin;
}
