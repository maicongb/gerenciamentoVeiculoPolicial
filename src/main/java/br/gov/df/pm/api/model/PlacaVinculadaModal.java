package br.gov.df.pm.api.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlacaVinculadaModal {

	private Long id;
	private String placa;
	private Integer ativo;
	private String numeroSei;
	private ViaturaResumoModal viatura;
	
}




