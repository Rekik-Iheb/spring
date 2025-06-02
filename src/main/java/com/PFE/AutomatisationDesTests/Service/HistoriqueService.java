package com.PFE.AutomatisationDesTests.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.PFE.AutomatisationDesTests.Entity.Historique;
import com.PFE.AutomatisationDesTests.Entity.Ligne_Comptable;
import com.PFE.AutomatisationDesTests.Repository.HistoriqueRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

@Service
public class HistoriqueService implements HistoriqueIService {
	@PersistenceContext
    private EntityManager entityManager;
	@Autowired
	private HistoriqueRepository repository;

	@Override
	public List<Historique> getAll(){
		List<Historique> list = new ArrayList<>() ;
		try {
		list  = repository.findAll();
		}catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
		}finally {
			return list;
		}
	}

	@Override
	public void ajouterHistorique(Historique h) {
		// TODO Auto-generated method stub
		try {
			if(h == null) return ; 
			Optional<Historique> op = repository.findByCodeContratAndUserAndDate(h.getCodeContrat(), h.getUser(), h.getDate());
			if(op.isEmpty()) {
				repository.save(h);
			}
 		}catch (Exception e) {
			// TODO: handle exception
 			System.out.println(e.getMessage());
		}
		
	}
	@Override
	@Transactional
	public List<Historique> search(Map<String, Object> criteria) {
	    StringBuilder queryString = new StringBuilder("SELECT h FROM Historique h WHERE 1=1 ");
	    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

	   
	    Map<String, Object> filters = (Map<String, Object>) criteria;

	  
        	   if(criteria.get("utilisateur") != null && !criteria.get("utilisateur").toString().isEmpty()) {
        	   queryString.append(" AND LOWER(h.user.nomPrenom) LIKE '%"+ criteria.get("utilisateur").toString().toLowerCase() + "%'");
        	   }
        	   

          
        	   if(criteria.get("codeContrat") != null  && !criteria.get("codeContrat").toString().isEmpty()) {
               queryString.append(" AND LOWER(h.codeContrat) LIKE '%"+ criteria.get("codeContrat").toString().toLowerCase() + "%'");
        	   }
        	  
        	   if(criteria.get("dateDebut") != null &&  !criteria.get("dateDebut").toString().isEmpty()) {
        	   queryString.append(" AND h.date >= :dateDebut");
        	   }       	   
               
        	  
        	   if(criteria.get("dateFin") != null && !criteria.get("dateFin").toString().isEmpty()) {
               queryString.append(" AND h.date <= :dateFin");
        	   }

             

           
        	   if(criteria.get("typeProduit") != null && !criteria.get("typeProduit").toString().isEmpty()) {
        	   queryString.append(" AND LOWER(h.produit.nom) LIKE '%"+ criteria.get("typeProduit").toString().toLowerCase() + "%'");
        	   }
        	  
        	   if(criteria.get("groupeProduit") != null && !criteria.get("groupeProduit").toString().isEmpty() ) {
        	   queryString.append(" AND LOWER(h.grProduit.nom) LIKE '%"+ criteria.get("groupeProduit").toString().toLowerCase() + "%'");
        	   }
        	
        	   if(criteria.get("statutTest") != null && !criteria.get("statutTest").toString().isEmpty()) {
        	   queryString.append(" AND LOWER(h.statutTest) LIKE '%"+ criteria.get("statutTest").toString().toLowerCase() + "%'");
        	   }
        	   if(criteria.get("action") != null && !criteria.get("action").toString().isEmpty()) {
            	   queryString.append(" AND LOWER(h.action) LIKE '%"+ criteria.get("action").toString().toLowerCase() + "%'");
            	   }
       
	    TypedQuery<Historique> query = entityManager.createQuery(queryString.toString(), Historique.class);
	    if(filters.get("dateFin") != null &&  !criteria.get("dateFin").toString().isEmpty()) {
           LocalDateTime dateFin = LocalDate.parse(criteria.get("dateFin").toString(), formatter).atTime(23, 59);
            query.setParameter("dateFin", dateFin);
        }
	    if(criteria.get("dateDebut") != null &&  !criteria.get("dateDebut").toString().isEmpty()) {
    	    LocalDateTime dateDebut = LocalDate.parse(criteria.get("dateDebut").toString(), formatter).atTime(0, 0);
    	    query.setParameter("dateDebut", dateDebut);}
	    
	    return query.getResultList();
	}

}
