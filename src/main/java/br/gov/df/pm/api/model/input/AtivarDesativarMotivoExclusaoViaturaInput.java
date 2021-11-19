package br.gov.df.pm.api.model.input;

import br.gov.df.pm.domain.enumeration.StatusMotivoExclusaoViatura;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AtivarDesativarMotivoExclusaoViaturaInput {
	
	private Long[] motivoExclusaoViaturaIds;
	private StatusMotivoExclusaoViatura status;
}
