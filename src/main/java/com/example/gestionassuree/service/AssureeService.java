package com.example.gestionassuree.service;

import com.example.gestionassuree.entity.Assuree;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

public interface AssureeService {

    List<Assuree> retrieveAllAssuree();
    Assuree retieveOneAssuree(Long id);
    Assuree addAssuree(Assuree assuree);
    void deleteAssuree(Long id);
}
