package br.gov.df.pm.api.model.input;

import br.gov.df.pm.domain.enumeration.StatusTipoViatura;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AtivarDesativarTipoViaturaInput {

	private Long[] tipoViaturaIds;
	private StatusTipoViatura status;
	
}
