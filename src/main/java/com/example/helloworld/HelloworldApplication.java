package com.example.helloworld;

import com.example.helloworld.entities.Etudiant;
import com.example.helloworld.repository.IEtudiantRepositoryJpa;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;
import java.util.stream.Stream;

@SpringBootApplication
public class HelloworldApplication {

	public static void main(String[] args) {

		SpringApplication.run(HelloworldApplication.class, args);
		System.out.println("Démarrage OK - port:8080 par défaut !!!");
	}
	//Quelques données
	//@Bean
	CommandLineRunner startCompte(IEtudiantRepositoryJpa iEtudiantRepositoryJpa){
		return args -> {
			Stream.of("Moh","Saly","Kaci","Margot")
					.forEach(nom -> {
						Etudiant etudiant = new Etudiant();
						etudiant.setNom(nom);
						etudiant.setNote(
								Math.random()<0.4?8:
										Math.random()<0.5?10:
												Math.random()<0.9?14:16
						);
						etudiant.setAdmis(etudiant.getNote() >= 10);

						iEtudiantRepositoryJpa.save(etudiant);

						//jdbcUserDetailsManager.createUser(User.withUsername(etudiant.getNom()).password(passwordEncoder.encode("1234")).roles("REDACT").build());

					});
		};
	}
}
