package com.PFE.AutomatisationDesTests.Service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.PFE.AutomatisationDesTests.Entity.Utilisateur;
import com.PFE.AutomatisationDesTests.Repository.UtilisateurRepository_;





@Service

public class UtilisateurService implements UtilisateurIService {

	@Autowired
	private UtilisateurRepository_ repo;
	
	@Override
	public String logIn(String email, String password) {
		// TODO Auto-generated method stub
		
		if(email.isEmpty() || password.isEmpty()) {
			return "Voulez-vous Entrer l'email et le mot de pass";
		}
		Utilisateur  u = repo.findByEmailAndSupprimerIsFalse(email);
		if(u != null && u.getPassword().equals(password)) {
			return u.getRole().toString();
		}
		return "Email/Mot de passe invalide veuillez essayer de nouveau";
	}
	@Override
	public List<Utilisateur> getAll() {
		// TODO Auto-generated method stub
		return repo.findBySupprimerIsFalse();
	}
	@Override
	public String save(Utilisateur u) {
		// TODO Auto-generated method stub
	String	rep = verifer(u);
		if(rep == null ) {
		try {	
			repo.save(u);
			return "L'utilisateur a été ajouté avec succès.";
		}catch (Exception e) {
			// TODO: handle exception
			return e.getMessage();
		}
		}else {
			return rep;
		}
		
	}
	@Override
	public List<Utilisateur> getUtilisateur(List<Object> c) {
		// TODO Auto-generated method stub
		try {	
			return null;
		//	return find.searchUtilisateur(c);
		}catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
			return null;
		}
	}
	@Override
	public String update(Utilisateur u) {
		// TODO Auto-generated method stub
	
		try {
			Utilisateur us = repo.findByEmailAndSupprimerIsFalse(u.getEmail());
			if(us == null ) {
				return "Verifer les informations du compte";
			}
			us.setNomPrenom(u.getNomPrenom());
			us.setPassword(u.getPassword());
			us.setRole(u.getRole());
			us.setTelephone(u.getTelephone());
		repo.save(us);
		return "le compte "+u.getEmail()+ " modifer avec succès";
		}catch(Exception e){
			return e.getMessage();
		}
		
	}
	@Override
	public String removeUtilisateur(String email) {
		// TODO Auto-generated method stub
		Utilisateur u = repo.findByEmailAndSupprimerIsFalse(email);
		if(u == null ) {
			return "l'utilisateur n'existe pas";
		}
		u.setSupprimer(true);
		repo.save(u);
		return "l'utilisateur supprimer avec succès";
	}
	@Override
	public String verifer(Utilisateur u) {
	    if (u.getEmail() == null || u.getEmail().isEmpty()) {
	        return "Veuillez vérifier l'email.";
	    }
	    String emailPattern = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
	    if (!u.getEmail().matches(emailPattern)) {
	        return "L'email n'est pas valide.";
	    }
	    Utilisateur ut= repo.findByEmailAndSupprimerIsFalse(u.getEmail());
	    if(ut != null) {
	    	return "L'email déjà utilisé .";
	    	}
	    if (u.getPassword() == null || u.getPassword().isEmpty()) {
	        return "Veuillez entrer un mot de passe.";
	    }
	    if (u.getPassword().length() < 8) {
	        return "Le mot de passe doit contenir au moins 8 caractères.";
	    }

	    if (u.getNomPrenom() == null || u.getNomPrenom().trim().isEmpty()) {
	        return "Veuillez entrer votre nom et prénom.";
	    }
	    String phone = String.valueOf(u.getTelephone());
	    if ( !(phone.startsWith("9") || phone.startsWith("2") || phone.startsWith("5") || phone.startsWith("4") || phone.startsWith("3"))) {
	        return "Veuillez entrer un numéro de téléphone valide.";
	    }
	   
	    if (phone.length() != 8) {
	        return "Le numéro de téléphone doit comporter exactement 8 chiffres.";
	    }
	  

	    if (u.getRole() == null) {
	        return "Veuillez sélectionner un rôle.";
	    }

	  

	    return null;
	}
	@Override
	public Utilisateur getUtilisateurbyEmail(String email) {
		// TODO Auto-generated method stub
		if(email=="") {
			return null;
		}
		Utilisateur u = repo.findByEmailAndSupprimerIsFalse(email);
		if(u == null ) {
			return null;
		}
		return u;
	}
	@Override
	public long count() {
		try {
		return repo.countBySupprimerFalse();}
		catch (Exception e) {	
			// TODO: handle exception
			System.out.println(e.getMessage());
			return 0 ;
		}
	}


	
	





}