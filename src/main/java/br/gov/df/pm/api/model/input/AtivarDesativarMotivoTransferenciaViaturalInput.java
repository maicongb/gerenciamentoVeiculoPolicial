package br.gov.df.pm.api.model.input;

import br.gov.df.pm.domain.enumeration.StatusMotivoTransferencia;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AtivarDesativarMotivoTransferenciaViaturalInput {
	
	private Long[] motivoTransferenciaViaturaIds;
	private StatusMotivoTransferencia status;
}
