package br.gov.df.pm.api.model.input;

import br.gov.df.pm.domain.enumeration.StatusTipoCombustivel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AtivarDesativarTipoCombustivelInput {

	private Long[] tipoCombustivelIds;
	private StatusTipoCombustivel status;
	
}
