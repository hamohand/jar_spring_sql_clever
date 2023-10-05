package com.example.helloworld.service;


import com.example.helloworld.entities.Etudiant;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IEtudiantService {
    //Create
    Etudiant creerFiche(Etudiant etudiant);
    //Read
    List<Etudiant> lire();
    List<Etudiant> lireAdmis();
    Etudiant lireFiche(Long id);
    //Update
    Etudiant modifierFiche(Etudiant etudiant,Long id);
    //Delete
    ResponseEntity<Object> supprimeFiche(Long id);

    // autres méthodes
    //Admis
    List<Etudiant> admis(double moyenne);
    //Ajourne
    List<Etudiant> ajourne(double moyenne);
    //Recherche
    List<Etudiant> recherche(String texte);
}
