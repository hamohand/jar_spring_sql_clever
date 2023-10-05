package com.example.helloworld.repository;




import com.example.helloworld.entities.Etudiant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
//@Repository // InjectDep
public interface IEtudiantRepositoryJpa extends JpaRepository<Etudiant,Long> {
    //find[type]sBy[FieldName][Contains]
    List<Etudiant> findEtudiantsByNomContains(String motcle);

    //Liste Admis
    @Query("SELECT a FROM Etudiant a WHERE a.admis")
    List<Etudiant> listeAdmis();
    //
    List<Etudiant> findEtudiantsByNoteGreaterThanEqual(double moyenne);

    //find[type]sBy[FieldName][LessThan]
    List<Etudiant> findEtudiantsByNoteLessThan(double moyenne);

/************ */
    //Créer un étudiant
    //@Query("INSERT INSERT INTO Etudiant (nom)  VALUES (?)")
  /*  @Query(
            value = "insert into Etudiant a.nom VALUES :x  ",
            nativeQuery = true)
    Etudiant creeEtud(@Param("x") String nom);

    //@Query("select a from Etudiant a where a.nom = :x")
    @Query(
            value = "SELECT * FROM Etudiant ORDER BY id",
            countQuery = "SELECT count(*) FROM Users",
            nativeQuery = true)
   List<Etudiant> listeEtudiants();*/

}
