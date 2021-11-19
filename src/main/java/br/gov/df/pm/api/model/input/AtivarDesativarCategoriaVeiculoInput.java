package br.gov.df.pm.api.model.input;

import br.gov.df.pm.domain.enumeration.StatusCategoriaVeiculo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AtivarDesativarCategoriaVeiculoInput {

	@ApiModelProperty(example = "1,2,3")
	private Long[] categoriaVeiculoIds;
	
	@ApiModelProperty(example = "ATIVAR")
	private StatusCategoriaVeiculo status;
	
}
