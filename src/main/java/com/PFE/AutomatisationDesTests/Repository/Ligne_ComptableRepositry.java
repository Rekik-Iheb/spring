package com.PFE.AutomatisationDesTests.Repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.PFE.AutomatisationDesTests.Entity.Ligne_Comptable;
@Repository
public interface Ligne_ComptableRepositry extends JpaRepository<Ligne_Comptable, Long> {

	public long countByFileName(String fileName);
	
	@Query("SELECT l FROM Ligne_Comptable l where l.codeContrat = :code ")
	public List<Ligne_Comptable> findByCode_Contrat(@Param("code") String code);
	@Query("SELECT l.transReference FROM Ligne_Comptable l where l.codeContrat = :code group by l.transReference")
	public List<String> findTrasnctionByCode_Contrat(@Param("code") String code);
	

	public List<Ligne_Comptable> findByTransReference(String transReference);
	@Query("SELECT MAX(l.amountLcy) FROM Ligne_Comptable l WHERE l.codeContrat = :code")
	public double getCreditAmount(@Param("code") String code);
	public long countByCodeContratAndProductCateg(String codeContrat, int product_Categ);
	

}