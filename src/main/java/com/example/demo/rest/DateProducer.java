package com.example.demo.rest;

import java.time.LocalDateTime;
import javax.enterprise.context.ApplicationScoped;
//import javax.ws.rs.Produces;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;

@ApplicationScoped
public class DateProducer {
   @Produces
	/*
	public String createEntityManager() {
		return "Hello the world";
	}
	*/
	
	
   public EntityManager createEntityManager() {
        System.out.println("******** entity manager from 'EntityManagerProducer'**************");
		return Persistence
                .createEntityManagerFactory("MyPU") //Il faut mettre les paramètres de connexion dans 'persistence.xml'. Si au lancement du server, il y a cette erreur
				//Caused by: org.postgresql.util.PSQLException: FATAL: authentification par mot de passe ÚchouÚe pour l'utilisateur  ½ postgres ╗ 
				//verifier le mdp.
				//verifier les paramètres de connexion avec la commande 'psql' qui se trouve 'dossier_installation/bin'
                .createEntityManager();
    }
     
    public void close(EntityManager entityManager) {
        entityManager.close();
    }
	
}

 
