package br.gov.df.pm.api.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoriaVeiculoModel {

	@ApiModelProperty(example = "1")
	private Long id;
	
	@ApiModelProperty(example = "PASSAGEIRO")
	private String nome;
	
	@ApiModelProperty(value = "ATIVO status da categoria do veículo", example = "1")
	private Integer ativo;
}
