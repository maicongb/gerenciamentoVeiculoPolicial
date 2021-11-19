package br.gov.df.pm.api.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItensVistoriaModel {

	@ApiModelProperty(example = "1")
	private Long id;
	
	@ApiModelProperty(example = "Pneu")
	private String nome;
	
	@ApiModelProperty(value = "ATIVO status do item da vistoria", example = "1")
	private Integer ativo;
	
	private TipoVistoriaModel tipoVistoria;
	
}
