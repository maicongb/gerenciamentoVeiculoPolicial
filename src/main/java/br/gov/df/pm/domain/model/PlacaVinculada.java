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
@Entity(name = "PLACAVINCULADA")
public class PlacaVinculada {
	
		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		@EqualsAndHashCode.Include
		@Column(name = "plv_Codigo")
		private Long id;
		
		@Column(name= "plv_Placa")
		private String placa;
		
		@Column(name= "plv_Ativo")
		private Integer ativo = 1;
		
		@Column(name = "plv_NrSei")
		private String numeroSei;
		
}
