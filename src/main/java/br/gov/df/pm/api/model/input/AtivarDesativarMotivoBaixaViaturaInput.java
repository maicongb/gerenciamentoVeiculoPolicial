package br.gov.df.pm.api.model.input;

import br.gov.df.pm.domain.enumeration.StatusMotivoBaixaViatura;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AtivarDesativarMotivoBaixaViaturaInput {
	
	private Long[] motivoBaixaViaturaIds;
	private StatusMotivoBaixaViatura status;
}
