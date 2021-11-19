package br.gov.df.pm.domain.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity(name = "UPM")
public class UnidadePolicialMilitar {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	@Column(name = "upm_Codigo")
	private Long id;

	@Column(name = "upm_Sigla", nullable = false)
	private String sigla;

	@Column(name = "upm_Nome", nullable = false)
	private String nome;

}
