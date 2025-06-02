package com.PFE.AutomatisationDesTests.Service;

import java.util.List;

import com.PFE.AutomatisationDesTests.Entity.Produit;

public interface ProduitIService {
	public List<Produit> getProduit(Long sfd) ;
	public Produit getProduitById(Long id) ;

}
