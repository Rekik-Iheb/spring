package com.PFE.AutomatisationDesTests.Controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.PFE.AutomatisationDesTests.Entity.Role;
import com.PFE.AutomatisationDesTests.Entity.Utilisateur;
import com.PFE.AutomatisationDesTests.Service.UtilisateurService;



@RestController
@CrossOrigin(origins = "*" )
@RequestMapping("/utilisateur")
public class UtilisateurControlleur {
	@Autowired
	private UtilisateurService utilisateurService;
  

	

	@PostMapping("/login")
	public ResponseEntity<String> login(@RequestBody Utilisateur u){
		if(u == null)
			return ResponseEntity.status(401).body("Error");
		String rep = utilisateurService.logIn(u.getEmail(), u.getPassword());
		if(rep.contains("Email")) {
			return ResponseEntity.status(400).body(rep);
			
		}else {
			return ResponseEntity.status(200).body(rep);
		}

	}
	@GetMapping("/role")
	public ResponseEntity<Role[]> getRole(){
		
		return ResponseEntity.status(200).body( Role.values());

	}
	@PostMapping("/")
	public ResponseEntity<String> save(@RequestBody Utilisateur u){
		if(u == null)
			return ResponseEntity.status(401).body("Error");
		String rep = utilisateurService.save(u);
		if(rep.contains("succès")) {
			return ResponseEntity.status(200).body(rep);
			
		}else {
			return ResponseEntity.status(400).body(rep);
		}

	}
	@GetMapping("/")
	public ResponseEntity<List<Utilisateur>> getAll(){
		
		List<Utilisateur> uts =utilisateurService.getAll();
		if(uts == null) {
			return ResponseEntity.status(400).body(null);
		}
		return ResponseEntity.status(200).body(uts);

	}
	@GetMapping("/getKey")
	public ResponseEntity<String> getKey(){
		
		
		return ResponseEntity.status(200).body("gac");

	}
	 @PostMapping("/find")
	 public ResponseEntity<?> getUser(@RequestBody	List<Object> requestData) {
			
		List<Utilisateur> us = utilisateurService.getUtilisateur(requestData);
		if(us == null) {
			 return  ResponseEntity.status(400).body(null);
		}
	     return ResponseEntity.ok(us);
	 }
	 @GetMapping("/{email}")
		public ResponseEntity<Utilisateur> getByEmail(@PathVariable("email") String email){
			System.out.println(email);
			Utilisateur ut =utilisateurService.getUtilisateurbyEmail(email);
			if(ut == null) {
				return ResponseEntity.status(400).body(null);
			}
			return ResponseEntity.status(200).body(ut);

		}
	 @PutMapping("/update")
		public ResponseEntity<String> update(@RequestBody Utilisateur u){
		 System.out.println(u);
			if(u == null)
				return ResponseEntity.status(401).body("Error");
			String rep = utilisateurService.update(u);
			if(rep.contains("succès")) {
				return ResponseEntity.status(200).body(rep);
				
			}else {
				return ResponseEntity.status(400).body(rep);
			}

		}
	 @PutMapping("/delet/{email}")
		public ResponseEntity<String> update(@PathVariable String email){
			if(email == null || email.isEmpty())
				return ResponseEntity.status(401).body("Error");
			String rep = utilisateurService.removeUtilisateur(email);
			if(rep.contains("succès")) {
				return ResponseEntity.status(200).body(rep);
				
			}else {
				return ResponseEntity.status(400).body(rep);
			}

		}
	 @GetMapping("/count")
		public ResponseEntity<Long> coun(){
			
			long ut =utilisateurService.count();
			
			return ResponseEntity.status(200).body(ut);

		}

    }
