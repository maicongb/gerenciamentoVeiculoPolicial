package br.gov.df.pm.api.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ModeloVeiculoResumoModel {

	private Long id;
	private String nome;
	private String potencia;
	private Integer tanque;
	private Integer importado;
	private Integer ativo;
	
}
