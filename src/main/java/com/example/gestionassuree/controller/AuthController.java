package com.example.gestionassuree.controller;

import com.example.gestionassuree.entity.Assuree;
import com.example.gestionassuree.entity.ERole;
import com.example.gestionassuree.entity.Role;
import com.example.gestionassuree.entity.Voiture;
import com.example.gestionassuree.feignClient.AssureeFeignClient;
import com.example.gestionassuree.repository.AssureeRepo;
import com.example.gestionassuree.repository.RoleRepository;

import com.example.gestionassuree.repository.VoitureRepo;
import com.example.gestionassuree.security.jwt.JwtUtils;
import com.example.gestionassuree.service.AssureeDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/assuree/auth")
public class AuthController {
  public AuthController(AssureeFeignClient assureeFeignClient){
    this.assureeFeignClient=assureeFeignClient;

  }
  AssureeFeignClient assureeFeignClient;

  @Autowired
  VoitureRepo voitureRepo;
  @Autowired
  AuthenticationManager authenticationManager;

  @Autowired
  AssureeRepo assureeRepository;

  @Autowired
  RoleRepository roleRepository;

  @Autowired
  PasswordEncoder encoder;

  @Autowired
  JwtUtils jwtUtils;

  @PostMapping("/signin")
  public ResponseEntity<?> authenticateUser(@Valid @RequestBody Assuree loginRequest) {

    Authentication authentication = authenticationManager
        .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

    SecurityContextHolder.getContext().setAuthentication(authentication);

    AssureeDetailsImpl assureeDetails = (AssureeDetailsImpl) authentication.getPrincipal();

    ResponseCookie jwtCookie = jwtUtils.generateJwtCookie(assureeDetails);

    List<String> roles = assureeDetails.getAuthorities().stream()
        .map(item -> item.getAuthority())
        .collect(Collectors.toList());


      return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
              .body(new Assuree(
                      assureeDetails.getId(),
                      assureeDetails.getUsername(),
                      assureeDetails.getNom(),
                      assureeDetails.getPrenom(),
                      assureeDetails.getEmail(),
                      assureeDetails.getPassword(),
                      assureeDetails.getCin(),
                      assureeDetails.getTelephone(),
                      assureeDetails.getAdresse(),
                      assureeDetails.getDateNaissance(),
                      assureeDetails.getNbreAccident(),
                      assureeDetails.getVoitures(),
                      assureeDetails.getAssureurId(),


                      roles));

  }

  @PostMapping("/signup")
  public ResponseEntity<?> registerUser(@Valid @RequestBody Assuree signUpRequest) {
    if (assureeRepository.existsByUsername(signUpRequest.getUsername())) {
      return ResponseEntity.badRequest().body("Error: Username is already taken!");
    }

    if (assureeRepository.existsByEmail(signUpRequest.getEmail())) {
      return ResponseEntity.badRequest().body("Error: Email is already in use!");
    }

    // Create new admin's account
    Assuree assuree = new Assuree();
    assuree.setUsername( signUpRequest.getUsername());
    assuree.setEmail( signUpRequest.getEmail());
    assuree.setPassword( encoder.encode(signUpRequest.getPassword()));
    assuree.setTelephone( signUpRequest.getTelephone());
    assuree.setPrenom( signUpRequest.getPrenom());
    assuree.setNom( signUpRequest.getNom());
    assuree.setAdresse( signUpRequest.getAdresse());
    assuree.setDateNaissance( signUpRequest.getDateNaissance());
    assuree.setCin( signUpRequest.getCin());
    assuree.setAssureurId( signUpRequest.getAssureurId());
    assuree.setNbreAccident( signUpRequest.getNbreAccident());
   // assuree.setVoitures( signUpRequest.getVoitures());
    List<Voiture> voitures= signUpRequest.getVoitures();
    assuree.setVoitures(new ArrayList<Voiture>());
    for (Voiture voiture : voitures) {
      assuree.getVoitures().add(voitureRepo.save(voiture));
    }

    List<String> strRoles = signUpRequest.getRole();
    Set<Role> roles = new HashSet<>();

    if (strRoles == null) {
      throw new ResponseStatusException(
              HttpStatus.NOT_ACCEPTABLE, "Empty");


    } else {
      strRoles.forEach(role -> {
        if(role.equals("ROLE_ASSUREE"))
            {
               Role assureeRole = roleRepository.findByName(ERole.ROLE_ASSUREE)
                     .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
               roles.add(assureeRole);
            }
        else{
          throw new ResponseStatusException(
                  HttpStatus.NOT_ACCEPTABLE, "Erreur : Vous n'avez pas l'authorisation pour accéder à cette page");
        }


      });
    }

    assuree.setRoles(roles);
     assureeRepository.save(assuree);
  /* String email= assuree.getEmail();
    String password=assuree.getPassword();
    String username=assuree.getUsername();
    assureeFeignClient.sendMailToAssuree(email,password,username);*/
    return ResponseEntity.ok("User registered successfully!");
  }


  @PostMapping("/signout")
  public ResponseEntity<?> logoutUtilisateur() {
    ResponseCookie cookie = jwtUtils.getCleanJwtCookie();
    return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString())
        .body("You've been signed out!");
  }
}
