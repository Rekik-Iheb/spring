package com.PFE.AutomatisationDesTests.Entity;




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
@ToString
@Entity
public class Utilisateur {

	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	private Long id;
	@Column(nullable = false )
	@NonNull
	private String email;
	@Column(nullable = false )
	@NonNull 
	private String password;
	@Column(nullable = false)
	@NonNull
	private String nomPrenom;
	@Column(length = 8 , nullable = false )
	@NonNull
	private String telephone;
	@NonNull
	@Enumerated(EnumType.STRING)
	private Role role;
	private boolean supprimer;
}
