package com.PFE.AutomatisationDesTests.Entity;


import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
@Table(
	    name = "historique", 
	    uniqueConstraints = {
	        @UniqueConstraint(columnNames = {"date", "user_id", "code_Contrat"})
	    }
	)
@ToString
public class Historique {

	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	private Long id;
	@NonNull
	@ManyToOne
	private Utilisateur user ; 
	private LocalDateTime date = LocalDateTime.now();
	@NonNull 
	@ManyToOne
	private Produit produit ;
	@NonNull 
	@ManyToOne
	private GroupeProduit grProduit;
	@NonNull 
	private String codeContrat;
	@NonNull 
	@Enumerated(EnumType.STRING)
	private TypeTest statutTest;
	@NonNull
	@Enumerated(EnumType.STRING)
	private Activite action;
}
