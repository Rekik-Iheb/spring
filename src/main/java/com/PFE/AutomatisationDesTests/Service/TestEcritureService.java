package com.PFE.AutomatisationDesTests.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.PFE.AutomatisationDesTests.Entity.Activite;
import com.PFE.AutomatisationDesTests.Entity.Historique;
import com.PFE.AutomatisationDesTests.Entity.Ligne_Comptable;
import com.PFE.AutomatisationDesTests.Entity.StatutTest;
import com.PFE.AutomatisationDesTests.Entity.TestEcriture;
import com.PFE.AutomatisationDesTests.Repository.TestEcritureRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

@Service
public class TestEcritureService implements TestEcritureIService {
	@PersistenceContext
    private EntityManager entityManager;
	@Autowired
	private HistoriqueService historiqueService;
	@Autowired 
	private TestEcritureRepository repository;
	
	@Override
	public TestEcriture getResultat(String code) {
		// TODO Auto-generated method stub
		TestEcriture test = null;
		try {
			if(repository.findByContrat(code) != null)
				test =repository.findByContrat(code);
		}catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
		}finally {
			return test;
		}
		
	}

	@Override
	public boolean ajouterTest(TestEcriture test) {
		// TODO Auto-generated method stub
		
		if(test == null ) {
			return false;
		}
		try {
			if(repository.findByContrat(test.getContrat()) == null) {
				repository.save(test);
				
			}else {
				TestEcriture t = repository.findByContrat(test.getContrat());
				t.setDateDeTest(test.getDateDeTest());
				t.setType(test.getType());
				t.setError(test.getError());
				t.setUser(test.getUser());
				t.setStatut(test.getStatut());
				t.setProduit(test.getProduit());
				t.setLu(false);
				t.setStatutValidation(test.getStatutValidation());
				t.setLignesTester(test.getLignesTester());
				repository.save(t);
			}
			return true;
		}catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
			return false;
		}
		
		
	}

	@Override
	public long count() {
		try {
		return repository.count();
		}
		catch (Exception e) {	
			// TODO: handle exception
			System.out.println(e.getMessage());
			return 0 ;
		}
	}

	@Override
	public List<TestEcriture> getNonLu() {
		// TODO Auto-generated method stub
		try {
			return repository.findByLuIsFalse();
		}catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
			return new ArrayList<>();
		}
	}
	
	@Override
	public List<TestEcriture> getAll() {
		// TODO Auto-generated method stub
		try {
			return repository.findAll();
		}catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
			return new ArrayList<>();
		}
	}

	@Override
	public List<ChartData> countStatut(int annee) {
		List<String> Moins = new ArrayList<>(Arrays.asList("Janvier", "Février", "Mars", "Avril", "Mai", "Juin","Juillet", "Août", "Septembre", "Octobre", "Novembre", "Décembre"));
		List<ChartData> data = new ArrayList<>();
		try {
			for (int i = 1; i <= 12; i++) {
				ChartData c = new ChartData();
				c.setAnnee(annee);
				c.setMois(Moins.get(i-1));
				c.setConcluant(repository.compterStatutsParMoisEtAnnee(i,annee,"TestConcluant"));
				c.setNonConcluant(repository.compterStatutsParMoisEtAnnee(i,annee,"TestNonConcluant"));
				data.add(c);
			}
		}catch (Exception e) {
			// TODO: handle exception
		}finally {
			return data;
		}
		
	}
	@Override
	public void setReaded(long id ) {
	try { Optional<TestEcriture> test = 	repository.findById(id);
	if(test.isEmpty()) return ; 
	TestEcriture t = test.get();
	t.setLu(true);
	repository.save(t);}catch (Exception e) {
		// TODO: handle exception
		System.out.println(e.getMessage());
	}
	}

	@Override
	public boolean validerTest(long id, StatutTest statut) {
		// TODO Auto-generated method stub
		
		try {
			Optional<TestEcriture>  opTest = repository.findById(id) ;
			if(opTest.isEmpty()) {
				return false;
			}
			TestEcriture  t = opTest.get();
			t.setStatutValidation(statut);
			Historique h = new Historique(t.getUser(),t.getProduit(),t.getProduit().getGroupeProduit(),t.getContrat(),t.getStatut(),Activite.VALIDATION);
			historiqueService.ajouterHistorique(h);
			repository.save(t);
			return true;
		}catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
			return false;
		}
		
	}

	@Override
	public List<TestEcriture> getStatutValidation(StatutTest statut) {
		// TODO Auto-generated method stub
		List<TestEcriture> list = null ;
		try {
			list =  repository.findByStatutValidation(statut);
		}catch (Exception e) {
			System.out.println(e.getMessage());
		}
		finally {
			return list;
		}
		
	}
	@Override
	@Transactional
	public List<TestEcriture> search(Map<String, Object> criteria) {
	 try {   StringBuilder queryString = new StringBuilder("SELECT t FROM TestEcriture t WHERE 1=1 ");
	    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

		   
	    Map<String, Object> filters = (Map<String, Object>) criteria;

	  
     	   if(criteria.get("utilisateur") != null && !criteria.get("utilisateur").toString().isEmpty()) {
     	   queryString.append(" AND LOWER(t.user.nomPrenom) LIKE '%"+ criteria.get("utilisateur").toString().toLowerCase() + "%'");
     	   }
     	   

       
     	   if(criteria.get("codeContrat") != null  && !criteria.get("codeContrat").toString().isEmpty()) {
            queryString.append(" AND LOWER(t.contrat) LIKE '%"+ criteria.get("codeContrat").toString().toLowerCase() + "%'");
     	   }
     	  
     	   if(criteria.get("dateDebut") != null &&  !criteria.get("dateDebut").toString().isEmpty()) {
     	   queryString.append(" AND t.dateDeTest >= :dateDebut");
     	   }       	   
            
     	  
     	   if(criteria.get("dateFin") != null && !criteria.get("dateFin").toString().isEmpty()) {
            queryString.append(" AND t.dateDeTest <= :dateFin");
     	   }

          

        
     	   if(criteria.get("statutValidation") != null && !criteria.get("statutValidation").toString().isEmpty()) {
     	   queryString.append(" AND t.statutValidation LIKE '"+ criteria.get("statutValidation") + "'");
     	   }
     	  

     	
     	   if(criteria.get("statutTest") != null && !criteria.get("statutTest").toString().isEmpty()) {
     	   queryString.append(" AND t.statut LIKE '%"+ criteria.get("statutTest") + "%'");
     	   }
     	   
    
	    TypedQuery<TestEcriture> query = entityManager.createQuery(queryString.toString(), TestEcriture.class);
	    if(filters.get("dateFin") != null &&  !criteria.get("dateFin").toString().isEmpty()) {
        LocalDate dateFin = LocalDate.parse(criteria.get("dateFin").toString(), formatter);
         query.setParameter("dateFin", dateFin);
     }
	    if(criteria.get("dateDebut") != null &&  !criteria.get("dateDebut").toString().isEmpty()) {
 	    LocalDate dateDebut = LocalDate.parse(criteria.get("dateDebut").toString(), formatter);
 	    query.setParameter("dateDebut", dateDebut);}
	    
	    return query.getResultList();}catch (Exception e) {
		System.out.println(e.getMessage());
		return null;
	}
	}
	@Override
	public List<Ligne_Comptable> getLignes(String contrat) {
	TestEcriture t  = repository.findByContrat(contrat);
	List<Ligne_Comptable> lignes = new ArrayList<>();
	if(t != null) {
		lignes.addAll(t.getLignesTester());
	}
	return lignes;
	}

	

}
