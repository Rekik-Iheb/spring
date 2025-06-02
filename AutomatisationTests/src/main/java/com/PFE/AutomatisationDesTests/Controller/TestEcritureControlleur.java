package com.PFE.AutomatisationDesTests.Controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.PFE.AutomatisationDesTests.Entity.Historique;
import com.PFE.AutomatisationDesTests.Entity.Ligne_Comptable;
import com.PFE.AutomatisationDesTests.Entity.StatutTest;
import com.PFE.AutomatisationDesTests.Entity.TestEcriture;
import com.PFE.AutomatisationDesTests.Service.ChartData;
import com.PFE.AutomatisationDesTests.Service.TestEcritureService;




@RestController
@CrossOrigin(origins = "*" )
@RequestMapping("/testecrutire")
public class TestEcritureControlleur {

	@Autowired
	private TestEcritureService service;
	 @GetMapping("/{code}")
	 public ResponseEntity<TestEcriture> getAll(@PathVariable("code") String code) {
			
			if( service.getResultat(code) != null ) {
				
				return ResponseEntity.ok(service.getResultat(code));}
			else {
				return ResponseEntity.status(400).body(null);
			}
		 }
	 @GetMapping("/count")
	 public ResponseEntity<Long> count(){

		 return ResponseEntity.status(200).body(service.count());

	 }
	 @GetMapping("/notification")
	 public ResponseEntity<List<TestEcriture>> getNotification() {
		 return ResponseEntity.status(200).body(service.getNonLu());
	 }
	 @GetMapping("/")
	 public ResponseEntity<List<TestEcriture>> getAll() {
		 return ResponseEntity.status(200).body(service.getAll());
	 }
	 
	 @GetMapping("/data/{annee}")
	 public ResponseEntity<List<ChartData>> getData(@PathVariable("annee") int annee){
		 
		
		 return ResponseEntity.status(200).body(service.countStatut(annee));
		 
	 }
	 @PutMapping("/{id}")
	 public void postMethodName(@PathVariable("id") long id) {
	 	service.setReaded(id);
	 }
	 @PutMapping("/validation/{id}")
	 public ResponseEntity<String> getStatutValidation(@PathVariable("id") long id , @RequestParam("statut") StatutTest statut){
		 
		if(service.validerTest(id , statut)) {
			return ResponseEntity.status(200).body("Le statut de validation du test a été mis à jour avec succès.");
		}else {
			return ResponseEntity.status(400).body("Erreur : le statut de validation du test n'a pas pu être mis à jour.");
		}
		
		
		 
	 }
	 @GetMapping("/validation")
	 public ResponseEntity<List<TestEcriture>> getStatutValidation(@RequestParam String statut) {
	     return ResponseEntity.status(200).body(service.getStatutValidation(StatutTest.valueOf(statut)));
	 }
	 @GetMapping("stauts")
	 public ResponseEntity<List<StatutTest>> getRole(){
			List<StatutTest> s = new ArrayList<>(List.of(StatutTest.VALIDE , StatutTest.ABANDONNE ,StatutTest.REJETE));
			return ResponseEntity.status(200).body(s);
		}
	
	 @PostMapping("/recherche")
	 public ResponseEntity<List<TestEcriture>> getLignes(@RequestBody Map<String, Object> requestData) {
		List<TestEcriture> ls = service.search(requestData);
	   if(ls == null)
	     return ResponseEntity.status(401).body(null);
	   return ResponseEntity.status(200).body(ls);
	 }
	 @GetMapping("/lignes/{contrat}")
	 public ResponseEntity<List<Ligne_Comptable>> getLignes(@PathVariable("contrat") String contrat) {
			List<Ligne_Comptable> ls = service.getLignes(contrat);
	   
			if(ls == null ) {
				 return  ResponseEntity.status(400).body(ls);
			}
	     return ResponseEntity.status(200).body(ls);
	 }
}
