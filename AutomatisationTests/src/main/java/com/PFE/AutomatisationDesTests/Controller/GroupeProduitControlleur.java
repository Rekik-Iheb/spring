package com.PFE.AutomatisationDesTests.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.PFE.AutomatisationDesTests.Entity.GroupeProduit;
import com.PFE.AutomatisationDesTests.Service.GroupeProduitService;

@RestController
@CrossOrigin(origins = "*" )
@RequestMapping("/groupeproduit")
public class GroupeProduitControlleur {

	@Autowired
	private GroupeProduitService service;


	

	@GetMapping("/")
	public ResponseEntity<List<GroupeProduit>> getSfd(){
		
		List<GroupeProduit> rep = service.getGroupeProduit();
		if(rep.isEmpty()) {
			return ResponseEntity.status(400).body(null);
			
		}else {
			return ResponseEntity.status(200).body(rep);
		}

	}
	

    
}
