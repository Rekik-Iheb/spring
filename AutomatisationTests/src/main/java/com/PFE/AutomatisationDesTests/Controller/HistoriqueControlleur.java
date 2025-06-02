package com.PFE.AutomatisationDesTests.Controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.PFE.AutomatisationDesTests.Entity.Historique;
import com.PFE.AutomatisationDesTests.Entity.Ligne_Comptable;
import com.PFE.AutomatisationDesTests.Service.HistoriqueService;



@RestController
@CrossOrigin(origins = "*" )
@RequestMapping("/historique")
public class HistoriqueControlleur {

	@Autowired
	private HistoriqueService historiqueService;
	 @GetMapping("/")
	 public ResponseEntity<List<Historique>> count() {
	 	return  ResponseEntity.status(200).body(historiqueService.getAll());
	 }
	 @PostMapping("/recherche")
	 public ResponseEntity<List<Historique>> getLignes(@RequestBody Map<String, Object> requestData) {
		List<Historique> ls = historiqueService.search(requestData);
	   if(ls == null)
	     return ResponseEntity.status(401).body(null);
	   return ResponseEntity.status(200).body(ls);
	 }
}
