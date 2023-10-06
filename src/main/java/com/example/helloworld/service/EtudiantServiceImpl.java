package com.example.helloworld.service;

import com.example.helloworld.entities.Etudiant;
import com.example.helloworld.repository.IEtudiantRepositoryJpa;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
@Service // InjectDep
//@Primary //prioritaire dans InjD
@AllArgsConstructor // obligatoire ?
public class EtudiantServiceImpl implements IEtudiantService{
    // CRUD service
    @Override
    public Etudiant creerFiche(Etudiant etudiant) {
        return iEtudiantRepositoryJpa.save(etudiant);
    }
    private IEtudiantRepositoryJpa iEtudiantRepositoryJpa ;
    @Override
    public List<Etudiant> lire() {
        return iEtudiantRepositoryJpa.findAll(Sort.by(Sort.Direction.ASC, "nom"));
    }

    @Override
    public List<Etudiant> lireAdmis() {
        return iEtudiantRepositoryJpa.listeAdmis();
    }

    @Override
    public Etudiant lireFiche(Long id) {
        return iEtudiantRepositoryJpa.findById(id)
                .orElseThrow(() -> new RuntimeException("Etudiant non trouvé"));
    }

    @Override
    public Etudiant modifierFiche(Etudiant etudiant, Long id) {
        Etudiant etudiantSilExiste = this.iEtudiantRepositoryJpa.findById(id)
                .orElseThrow(() -> new RuntimeException("Etudiant non trouvé"));
        etudiantSilExiste.setNom(etudiant.getNom());
        etudiantSilExiste.setNote(etudiant.getNote());
        etudiantSilExiste.setAdmis(etudiantSilExiste.getNote() >= 10);
        return this.iEtudiantRepositoryJpa.save(etudiantSilExiste);
    }

    @Override
    //public String supprimeFiche(Long id) {
    public ResponseEntity<Object> supprimeFiche(Long id) {
        Etudiant etudiantSilExiste = this.iEtudiantRepositoryJpa.findById(id)
                .orElseThrow(() -> new RuntimeException("Etudiant non trouvé avec id="+id));
        /*this.iEtudiantRepositoryJpa.deleteById(id);
        return "Produit supprimé";*/
        this.iEtudiantRepositoryJpa.delete(etudiantSilExiste);
        return ResponseEntity.ok().build();
    }
    @Override
    public List<Etudiant> admis(double moyenne) {
        return iEtudiantRepositoryJpa.findEtudiantsByNoteGreaterThanEqual(moyenne);
        //findBy[FieldName][GreaterThan] à créer dans l'interface iEtudiantRepositoryJpa
    }
    // autres requêtes non héritées donc sans @Override
    @Override
    public List<Etudiant> ajourne(double moyenne){
        return iEtudiantRepositoryJpa.findEtudiantsByNoteLessThan(moyenne);
    }
    @Override
    public List<Etudiant> recherche(String texte) {
        return iEtudiantRepositoryJpa.findEtudiantsByNomContains(texte);
    }

    //autres requêtes non héritées donc sans @Override
    public boolean resultat(double moyenne) {
        //return iEtudiantRepositoryJpa.;
return false; //à refaire
    }
}

