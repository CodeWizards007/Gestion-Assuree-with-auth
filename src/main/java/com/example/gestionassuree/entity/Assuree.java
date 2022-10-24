package com.example.gestionassuree.entity;

import com.example.gestionassuree.model.Assureur;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Assuree {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String nom;
    private String prenom;
    private String email;
    private String password;
    private Long cin;
    private Long telephone;
   private String adresse;
    @CreatedDate
    private LocalDateTime dateNaissance;
    private Integer nbreAccident;
    @OneToMany
    private List<Voiture> voitures;
    private Long AssureurId;
    @Transient
    private Assureur assureur;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "utilisateur_roles",
            joinColumns = @JoinColumn(name = "utilisateur_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();


    @Transient
    private List<String> role;


    public Assuree(Long id, String username, String nom, String prenom, String email, String password, Long cin, Long telephone, String adresse, LocalDateTime dateNaissance, Integer nbreAccident, List<Voiture> voitures, Long assureurId, List<String> role) {
        this.id = id;
        this.username = username;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.password = password;
        this.cin = cin;
        this.telephone = telephone;
        this.adresse = adresse;
        this.dateNaissance = dateNaissance;
        this.nbreAccident = nbreAccident;
        this.voitures = voitures;
        AssureurId = assureurId;
        this.role = role;
    }
}
