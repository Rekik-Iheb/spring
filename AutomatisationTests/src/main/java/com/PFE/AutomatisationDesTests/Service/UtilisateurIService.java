package com.PFE.AutomatisationDesTests.Service;

import java.util.List;

import com.PFE.AutomatisationDesTests.Entity.Utilisateur;

public interface UtilisateurIService {

	public String logIn(String email , String password);
	public List<Utilisateur> getAll();
	public String save(Utilisateur u);
	public List<Utilisateur> getUtilisateur(List<Object> c);
	public String update(Utilisateur u);
	public String removeUtilisateur(String email);
	public String verifer(Utilisateur u);
	public Utilisateur getUtilisateurbyEmail(String email);
	public long count();
}
