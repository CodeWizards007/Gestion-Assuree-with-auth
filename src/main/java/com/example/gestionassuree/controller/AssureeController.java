package com.example.gestionassuree.controller;

import com.example.gestionassuree.entity.Assuree;
import com.example.gestionassuree.entity.Voiture;
import com.example.gestionassuree.feignClient.AssureeFeignClient;
import com.example.gestionassuree.repository.VoitureRepo;
import com.example.gestionassuree.service.AssureeServiceImpl;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AssureeController {
    AssureeServiceImpl assureeService;
    AssureeFeignClient assureeFeignClient;

    VoitureRepo voitureRepo;
    public  AssureeController(AssureeServiceImpl assureeService, AssureeFeignClient assureeFeignClient, VoitureRepo voitureRepo){
        this.assureeService=assureeService;
       this.assureeFeignClient=assureeFeignClient;
       this.voitureRepo=voitureRepo;
    }

    @PostMapping("/addassuree")
    public Assuree AddOneAssuree(@RequestBody Assuree assuree){
        assureeService.addAssuree(assuree);
        String email= assuree.getEmail();
        String password=assuree.getPassword();
        String username=assuree.getPassword();
        assureeFeignClient.sendMailToAssuree(email,password,username);
        return assuree;
    }
    @DeleteMapping("/deleteAssuree/{id}")
    public void removeAssureeById(@PathVariable Long id){
         assureeService.deleteAssuree(id);
    }
    @GetMapping("/" +
            "")
    public List<Assuree> getAllAssuree(){
       return assureeService.retrieveAllAssuree();
    }
    @GetMapping("/assurees/{id}")
    public Assuree getAssuree(@PathVariable Long id){
       return assureeService.retieveOneAssuree(id);
    }
    @PostMapping("/AffectVoitureToAssuree")
    public Assuree affecterVoitureAssuree (@RequestBody List<Voiture> voitures,@RequestBody Assuree assuree){

       Assuree assuree1= assureeService.addAssuree(assuree);

      for (Voiture voiture:assuree.getVoitures()){

          assuree1.getVoitures().add(voitureRepo.save(voiture));

      }
     return assureeService.addAssuree(assuree1);


    }
    @GetMapping("/assuree")
    public List<Assuree> getAll (){
       return assureeService.retrieveAllAssuree();
    }
}
