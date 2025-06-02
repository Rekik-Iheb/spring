package com.PFE.AutomatisationDesTests.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.PFE.AutomatisationDesTests.Entity.Historique;
import com.PFE.AutomatisationDesTests.Entity.Utilisateur;

@Repository
public interface HistoriqueRepository extends JpaRepository<Historique, Long> {

	public Optional<Historique> findByCodeContratAndUserAndDate(String codeContrat, Utilisateur user, LocalDateTime date);

	
}
