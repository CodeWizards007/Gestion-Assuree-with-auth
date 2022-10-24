package com.example.gestionassuree;

import com.example.gestionassuree.entity.Assuree;
import com.example.gestionassuree.entity.ERole;
import com.example.gestionassuree.entity.Role;
import com.example.gestionassuree.entity.Voiture;
import com.example.gestionassuree.repository.AssureeRepo;
import com.example.gestionassuree.repository.RoleRepository;
import com.example.gestionassuree.repository.VoitureRepo;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;

import java.util.Collection;
import java.util.Date;
import java.util.List;

@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients
public class GestionAssureeApplication {

    public static void main(String[] args) {
      //  SpringApplication.run(GestionAssureeApplication.class, args);

        ConfigurableApplicationContext context = SpringApplication.run(GestionAssureeApplication.class, args);

        RoleRepository roleRepository = context.getBean(RoleRepository.class);



        for (ERole d : ERole.values()) {


            if (roleRepository.findByName(d).isEmpty()) {
                Role role = new Role();
                role.setName(d);
                roleRepository.save(role);


            }


        }
    }
   /* @Bean
    CommandLineRunner start(AssureeRepo assureeRepo, VoitureRepo voitureRepo){
        return args -> {
          voitureRepo.save(new Voiture(null,1555L,15,new Date(),155.88,1L,null));
          voitureRepo.save(new Voiture(null,1555L,15,new Date(),155.88,1L,null));
          assureeRepo.save(new Assuree(null,"riadh","yahyaoui","riadh@gmail.com","aaa",122L,2555555l,"188 avenue",new Date(),1,null,1L,null));
          List<Voiture> voitureList= voitureRepo.findAll();
          Assuree assuree= assureeRepo.findById(1L).get();
          assuree.setVoitures(voitureList);
          assureeRepo.save(assuree);*/

        }


