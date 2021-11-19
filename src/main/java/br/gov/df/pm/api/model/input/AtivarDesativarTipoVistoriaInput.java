package br.gov.df.pm.api.model.input;

import br.gov.df.pm.domain.enumeration.StatusTipoVistoria;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AtivarDesativarTipoVistoriaInput {

	private Long[] tipoVistoriaIds;
	private StatusTipoVistoria status;
	
}
