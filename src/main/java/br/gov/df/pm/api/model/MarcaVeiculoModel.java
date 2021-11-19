package br.gov.df.pm.api.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MarcaVeiculoModel {

	private Long id;
	private String nome;
	private String abreviacao;
	private Integer ativo;
	private TipoVeiculoModel tipoVeiculo;
	
}
