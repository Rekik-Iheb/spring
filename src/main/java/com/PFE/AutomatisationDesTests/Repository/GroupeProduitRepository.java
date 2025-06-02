package com.PFE.AutomatisationDesTests.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.PFE.AutomatisationDesTests.Entity.GroupeProduit;

@Repository
public interface GroupeProduitRepository extends JpaRepository<GroupeProduit, Long> {
	
	

}
