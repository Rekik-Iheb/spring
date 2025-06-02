package com.PFE.AutomatisationDesTests.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.PFE.AutomatisationDesTests.Entity.Produit;
import com.PFE.AutomatisationDesTests.Service.ProduitService;




@RestController
@CrossOrigin(origins = "*" )
@RequestMapping("/prod")
public class ProduitControlleur {
	@Autowired
	private ProduitService service;


	@GetMapping("/{id}")
	public ResponseEntity<List<Produit>> getSfd(@PathVariable("id") Long groupe){
		
		List<Produit> rep = service.getProduit(groupe);
		if(rep== null ) {
			return ResponseEntity.status(400).body(null);
			
		}else {
			
		
			return ResponseEntity.status(200).body(rep);
		}
	}
}
