package com.PFE.AutomatisationDesTests.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.PFE.AutomatisationDesTests.Entity.Ligne_Comptable;
import com.PFE.AutomatisationDesTests.Service.Ligne_ComptableService;



@RestController
@CrossOrigin(origins = "*" )
@RequestMapping("/ligne")
public class LigneControlleur {
 
	@Autowired
	private Ligne_ComptableService ligneService;
	
	 @PostMapping("/upload")
	 public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
		String rep =  ligneService.upload(file);
		if(rep.contains("succ")) {
			return ResponseEntity.status(200).body(rep);
		}
		return ResponseEntity.status(400).body(rep);
	 }

	 @PostMapping("/")
	 public ResponseEntity<List<Ligne_Comptable>> getLignes(@RequestBody List<Object> requestData) {
			List<Ligne_Comptable> ls = ligneService.searchLignes(requestData);
	   
			if(ls == null ) {
				 return  ResponseEntity.status(400).body(ls);
			}
	     return ResponseEntity.status(200).body(ls);
	 }
	 @PostMapping("/{code}/{email}/{prod}/{test}")
	 public ResponseEntity<String> getAll(@PathVariable("code") String code,@PathVariable("email") String email , @PathVariable("prod") Long prod ,@PathVariable("test") boolean test) {
		String rep = ligneService.test(code,email,prod, test);
		if( rep.contains("Le code du contrat est incorrect") == false ) {
				return ResponseEntity.ok(rep);
		}else {
			return ResponseEntity.status(400).body("Le code du contrat est incorrect");
		}
	 }
	 @GetMapping("/count")
	 public ResponseEntity<Long> count() {
	 	return  ResponseEntity.status(200).body(ligneService.count());
	 }
	 
	
}
