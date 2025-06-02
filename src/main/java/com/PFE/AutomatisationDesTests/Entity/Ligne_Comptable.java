package com.PFE.AutomatisationDesTests.Entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
@Entity
@ToString
public class Ligne_Comptable {
	
	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	private Long id;
	private String tab;
	private String recId;
	private String ourReference;
	private String transReference;
	private String codeContrat;
	private String customerId;
	private String accountNumber;
	private String accountOfficer;
	private int productCateg;
	private String transactionCode;
	private String narrative;
	@Column(length = 1000)
	private String narrative_6;
	private String exchangeRate;
	private double amountFcy;
	private double amountLcy;
	private String  currency;
	private  LocalDate bookingDate;
	private LocalDate valueDate;
	private String dealerDesk;
	private String systemId;
	@NonNull
	private LocalDate date;
	private String toChar;
	private String consolKey;
	private String fileName;
	@Enumerated(EnumType.STRING)
	private StatutLigne statut = StatutLigne.NonIdentifer ;
	

	
	
	

}
