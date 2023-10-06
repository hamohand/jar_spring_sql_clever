package com.example.helloworld.controller;

import com.example.helloworld.entities.Etudiant;
import com.example.helloworld.service.IEtudiantService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController // résultat au format JSON
@CrossOrigin(origins = "*", maxAge = 3600)// autorise l'accès du frontEnd
@AllArgsConstructor
public class EtudiantRestApi {

    private  final IEtudiantService iEtudiantService;

    //Accueil
    @GetMapping("/")
    String hello() {
        return "JAR-SQL-react, sur CleverCloud" + "Muhend ! le 06/10 18:00";
    }

    //Create
    @PostMapping("/etudiant")
    Etudiant creerEtudiant(@RequestBody Etudiant etudiant){
        return  iEtudiantService.creerFiche(etudiant);
    }

    //Read liste
    @GetMapping("/etudiants")  //url : http://localhost:8080/etudiants
    List<Etudiant> maListe(){
        return iEtudiantService.lire();
    }

    //Read one
    //@GetMapping("/etudiant/{id}")
    @GetMapping("/etudiant/{id}")
    //@PreAuthorize("hasRole('ROLE_ADMIN')")
    Etudiant afficheEtudiant(@PathVariable Long id){
        return iEtudiantService.lireFiche(id);
    }

    //Update
    @PutMapping("/etudiant/{id}")
    Etudiant modifieEtudiant(@RequestBody Etudiant etudiant, @PathVariable Long id){
        return iEtudiantService.modifierFiche(etudiant, id);
    }

    //Delete
    @DeleteMapping("/etudiant/{id}")
    public void supprimeEtudiant(@PathVariable Long id){
        iEtudiantService.supprimeFiche(id);
    }

    //Requêtes
    @GetMapping("/admis/{moyenne}")
    List<Etudiant> admis(@PathVariable double moyenne){
        return iEtudiantService.admis(moyenne);
    }
    @GetMapping("/ajourne/{moyenne}")
    List<Etudiant> ajourne(@PathVariable double moyenne){
        return iEtudiantService.ajourne(moyenne);
    }
    @GetMapping("/recherche/texte")
    List<Etudiant> recherche(@PathVariable String texte){
        return iEtudiantService.recherche(texte);
    }

}
