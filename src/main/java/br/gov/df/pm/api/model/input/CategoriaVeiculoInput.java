package br.gov.df.pm.api.model.input;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoriaVeiculoInput {
	
	@ApiModelProperty(example = "1", required = true)
	private Long id;
	
	@ApiModelProperty(example = "Passageiro", required = true)
	@NotBlank
	private String nome;
	
	@ApiModelProperty(example = "1", required = true)
	private Integer ativo;

}
