package com.PFE.AutomatisationDesTests.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.PFE.AutomatisationDesTests.Entity.Utilisateur;


@Repository
public interface UtilisateurRepository_ extends JpaRepository<Utilisateur, Long> {
	
	
	public Utilisateur findByEmailAndSupprimerIsFalse(String email);
	public List<Utilisateur> findBySupprimerIsFalse();
    public long countBySupprimerFalse();

}
