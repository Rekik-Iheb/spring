package com.PFE.AutomatisationDesTests.Service;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ChartData {

	private int annee;
	private String mois ;
	private long nonConcluant;
	private long concluant;
	
}
