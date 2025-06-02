package com.PFE.AutomatisationDesTests.Service;

import java.util.List;
import java.util.Map;

import com.PFE.AutomatisationDesTests.Entity.Historique;
import com.PFE.AutomatisationDesTests.Entity.Ligne_Comptable;
import com.PFE.AutomatisationDesTests.Entity.StatutTest;
import com.PFE.AutomatisationDesTests.Entity.TestEcriture;

public interface TestEcritureIService {

	public TestEcriture getResultat(String code);
	public boolean ajouterTest(TestEcriture test);
	public long count();
	public List<TestEcriture> getNonLu();
	public List<TestEcriture> getAll();
	public List<ChartData> countStatut(int annee);
	public void setReaded(long id );
	public boolean validerTest(long id , StatutTest statut );
	public List<TestEcriture> getStatutValidation(StatutTest statut);
	public List<TestEcriture> search(Map<String, Object> criteria);
	public List<Ligne_Comptable> getLignes(String contrat);
}
