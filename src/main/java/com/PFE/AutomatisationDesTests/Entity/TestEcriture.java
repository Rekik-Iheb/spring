package com.PFE.AutomatisationDesTests.Entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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
@ToString
@Entity
public class TestEcriture {

	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@ManyToOne
	private Utilisateur user;
	@NonNull
	@Column(unique = true)
	private String contrat;
	@Column(length = 10000)
	private String error = "";
	@OneToMany(cascade = CascadeType.ALL)
	private List<TypeEcritureComptable> type = new ArrayList<>();
	@NonNull
	@Enumerated(EnumType.STRING)
	private TypeTest statut;
	@NonNull
	@Enumerated(EnumType.STRING)
	@Column(name = "statut_validation")
	private StatutTest statutValidation;
	@NonNull
	private LocalDate dateDeTest= LocalDate.now();
	@ManyToOne
	private Produit produit;
	@OneToMany 
	private List<Ligne_Comptable> lignesTester; 
	private boolean lu;
} 


	
	