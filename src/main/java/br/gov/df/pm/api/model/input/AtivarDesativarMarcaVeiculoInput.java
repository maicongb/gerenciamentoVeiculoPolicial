package br.gov.df.pm.api.model.input;

import br.gov.df.pm.domain.enumeration.StatusMarcaVeiculo;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AtivarDesativarMarcaVeiculoInput {

	private Long[] marcaVeiculoIds;
	private StatusMarcaVeiculo status;
	
}
