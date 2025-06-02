package com.PFE.AutomatisationDesTests.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.PFE.AutomatisationDesTests.Entity.GroupeProduit;
import com.PFE.AutomatisationDesTests.Repository.GroupeProduitRepository;


@Service

public class GroupeProduitService implements GroupeProduitIService{

	@Autowired
	private GroupeProduitRepository repository;
	
	@Override
	public List<GroupeProduit> getGroupeProduit() {
		// TODO Auto-generated method stub
		return repository.findAll();
	}
	
}
