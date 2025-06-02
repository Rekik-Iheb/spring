package com.PFE.AutomatisationDesTests.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.PFE.AutomatisationDesTests.Entity.Produit;
@Repository
public interface ProduitRepository extends JpaRepository<Produit, Long> {

	public List<Produit> findByGroupeProduitId(Long groupeProduitId);	
}
