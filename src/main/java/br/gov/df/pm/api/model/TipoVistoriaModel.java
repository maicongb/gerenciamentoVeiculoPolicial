package br.gov.df.pm.api.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TipoVistoriaModel {
	
	@ApiModelProperty(example = "1")
	private Long id;
	
	@ApiModelProperty(example = "INSPEÇÃO DO MOTORISTA DURANTE O SERVIÇO")
	private String nome;
	
	@ApiModelProperty(value = "ATIVO status tipo de vistoria", example = "1")
	private Integer ativo;
	
}
