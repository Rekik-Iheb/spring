package com.PFE.AutomatisationDesTests.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.PFE.AutomatisationDesTests.Entity.StatutTest;
import com.PFE.AutomatisationDesTests.Entity.TestEcriture;
import com.PFE.AutomatisationDesTests.Entity.Utilisateur;


public interface TestEcritureRepository extends JpaRepository<TestEcriture, Long> {

	public TestEcriture findByContrat(String contrat);

	public List<TestEcriture> findByUser(Utilisateur user);

	public List<TestEcriture> findByLuIsFalse();
	@Query(value = "SELECT COUNT(*) FROM test_ecriture WHERE MONTH(date_de_test) = :month AND YEAR(date_de_test) = :year AND statut = :statut", nativeQuery = true)
	public long compterStatutsParMoisEtAnnee(@Param("month") int month,@Param("year") int year,@Param("statut") String statut);
	
	public List<TestEcriture> findByStatutValidation(StatutTest statutValidation);
}
