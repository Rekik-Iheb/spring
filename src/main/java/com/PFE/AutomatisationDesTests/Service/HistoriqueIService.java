package com.PFE.AutomatisationDesTests.Service;

import java.util.List;
import java.util.Map;

import com.PFE.AutomatisationDesTests.Entity.Historique;
import com.PFE.AutomatisationDesTests.Entity.Ligne_Comptable;

public interface HistoriqueIService {
	public List<Historique> getAll();
	public void ajouterHistorique(Historique h);
	public List<Historique> search(Map<String, Object> criteria);
}
