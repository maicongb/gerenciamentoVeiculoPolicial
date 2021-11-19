package br.gov.df.pm.api.model.input;

import br.gov.df.pm.domain.enumeration.StatusItensVistoria;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AtivarDesativarItensVistoriaInput {

	private Long[] itensVistoriaIds;
	private StatusItensVistoria status;
	
}
