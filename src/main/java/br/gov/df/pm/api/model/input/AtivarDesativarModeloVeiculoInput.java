package br.gov.df.pm.api.model.input;

import br.gov.df.pm.domain.enumeration.StatusModeloVeiculo;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AtivarDesativarModeloVeiculoInput {

	private Long[] modeloVeiculoIds;
	private StatusModeloVeiculo status;
	
}
