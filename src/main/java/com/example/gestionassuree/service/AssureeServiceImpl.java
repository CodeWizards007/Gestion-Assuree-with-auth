package com.example.gestionassuree.service;

import com.example.gestionassuree.entity.Assuree;
import com.example.gestionassuree.feignClient.AssureeFeignClient;
import com.example.gestionassuree.model.Assureur;
import com.example.gestionassuree.repository.AssureeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AssureeServiceImpl implements AssureeService {

    @Autowired
    private AssureeRepo assureeRepo;
  /*  @Autowired
    private AssureeFeignClient assureeFeignClient;*/


    @Override
    public List<Assuree> retrieveAllAssuree() {
       return assureeRepo.findAll();
      /* List<Assuree> assureeList = assureeRepo.findAll();

       for (Assuree assuree: assureeList){
          Long assureurId= assuree.getAssureurId();
          Assureur assureur = assureeFeignClient.getAssureurById(assureurId);
          assuree.setAssureur(assureur);
       }
       return  assureeList;*/
    }

    @Override
    public Assuree retieveOneAssuree(Long id) {
      /*  Assuree assuree=assureeRepo.findById(id).get();
        Long assureurId=assuree.getAssureurId();
        Assureur assureur=assureeFeignClient.getAssureurById(assureurId);
        assuree.setAssureur(assureur);
        return assuree;*/
       return assureeRepo.findById(id).get();
    }

    @Override
    public Assuree addAssuree(Assuree assuree) {
        return assureeRepo.save(assuree);
    }


    @Override
    public void deleteAssuree(Long id) {
        assureeRepo.deleteById(id);
    }
}
