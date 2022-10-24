package com.example.gestionassuree.service;


import com.example.gestionassuree.entity.Assuree;
import com.example.gestionassuree.entity.Voiture;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.OneToMany;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@ToString
public class AssureeDetailsImpl implements UserDetails {
  private static final long serialVersionUID = 1L;

  private Long id;
  private String username;
  private String nom;

  private String prenom;
  private String email;
  private String password;
  private Long cin;
  private Long telephone;
  private String adresse;

  private LocalDateTime dateNaissance;
  private Integer nbreAccident;

  private List<Voiture> voitures;
  private Long AssureurId;

  private Collection<? extends GrantedAuthority> authorities;

  public AssureeDetailsImpl(Long id, String username, String nom, String prenom, String email, String password,Long cin, Long telephone,String adresse,LocalDateTime dateNaissance,Integer nbreAccident,List<Voiture> voitures,Long assureurId,
                            Collection<? extends GrantedAuthority> authorities) {
    this.id = id;
    this.username = username;
    this.email = email;
    this.nom = nom;
    this.password = password;
    this.prenom = prenom;
    this.telephone = telephone;
    this.adresse = adresse;
    this.dateNaissance = dateNaissance;
    this.AssureurId = assureurId;
    this.nbreAccident = nbreAccident;
    this.voitures = voitures;
    this.cin = cin;
    this.authorities = authorities;
  }

  public static AssureeDetailsImpl build(Assuree assuree) {
    List<GrantedAuthority> authorities = assuree.getRoles().stream()
        .map(role -> new SimpleGrantedAuthority(role.getName().name()))
        .collect(Collectors.toList());

    return new AssureeDetailsImpl(
        assuree.getId(),
        assuree.getUsername(),
            assuree.getNom(),
            assuree.getPrenom(),
        assuree.getEmail(),
        assuree.getPassword(),
        assuree.getCin(),
        assuree.getTelephone(),
        assuree.getAdresse(),
        assuree.getDateNaissance(),
        assuree.getNbreAccident(),
        assuree.getVoitures(),
        assuree.getAssureurId(),


        authorities);
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return authorities;
  }

  public Long getId() {
    return id;
  }

  public String getEmail() {
    return email;
  }

  public String getNom() {
    return nom;
  }

  public String getPrenom() {
    return prenom;
  }

  public Long getTelephone() {
    return telephone;
  }

  public Long getCin() {
    return cin;
  }

  public String getAdresse() {
    return adresse;
  }

  public LocalDateTime getDateNaissance() {
    return dateNaissance;
  }

  public Integer getNbreAccident() {
    return nbreAccident;
  }

  public List<Voiture> getVoitures() {
    return voitures;
  }

  public Long getAssureurId() {
    return AssureurId;
  }

  @Override
  public String getPassword() {
    return password;
  }

  @Override
  public String getUsername() {
    return username;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;
    AssureeDetailsImpl utilisateur = (AssureeDetailsImpl) o;
    return Objects.equals(id, utilisateur.id);
  }
}
