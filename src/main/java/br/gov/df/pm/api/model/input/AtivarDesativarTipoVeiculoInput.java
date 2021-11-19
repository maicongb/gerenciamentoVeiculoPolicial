package br.gov.df.pm.api.model.input;

import br.gov.df.pm.domain.enumeration.StatusTipoVeiculo;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AtivarDesativarTipoVeiculoInput {

	private Long[] tipoVeiculoIds;
	private StatusTipoVeiculo status;
	
}
