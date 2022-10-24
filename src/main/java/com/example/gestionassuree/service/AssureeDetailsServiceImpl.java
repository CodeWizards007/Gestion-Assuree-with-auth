package com.example.gestionassuree.service;


import com.example.gestionassuree.entity.Assuree;
import com.example.gestionassuree.repository.AssureeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AssureeDetailsServiceImpl implements UserDetailsService {
  @Autowired
  AssureeRepo assureeRepo;

  @Override
  @Transactional
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    Assuree utilisateur = assureeRepo.findByUsername(username)
        .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));

    return AssureeDetailsImpl.build(utilisateur);
  }

}
