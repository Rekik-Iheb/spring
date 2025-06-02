package com.PFE.AutomatisationDesTests.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.PFE.AutomatisationDesTests.Entity.Produit;
import com.PFE.AutomatisationDesTests.Repository.ProduitRepository;

@Service
public class ProduitService implements ProduitIService{

	@Autowired
	private ProduitRepository repository;
	@Override
	public List<Produit> getProduit(Long groupeProduit) {
		List<Produit> res = new ArrayList<>();
		if(repository.findByGroupeProduitId(groupeProduit).size() > 0) {
			res = repository.findByGroupeProduitId(groupeProduit);
		}
		return res;
	}
	@Override
	public Produit getProduitById(Long id) {
		// TODO Auto-generated method stub
		Optional<Produit> op = repository.findById(id);
		if(op.isEmpty()) {
			return null;
		}
		return op.get();
	}


	
}
