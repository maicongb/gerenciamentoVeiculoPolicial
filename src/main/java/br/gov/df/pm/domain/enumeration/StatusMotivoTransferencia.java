package br.gov.df.pm.domain.enumeration;

import br.gov.df.pm.domain.repository.MotivoTransferenciaViaturaRepository;

public enum StatusMotivoTransferencia {

	ATIVAR {
		@Override
		public void executar(Long[] motivoTransferenciaIds,
				MotivoTransferenciaViaturaRepository motivoTransferenciaViaturaRepository) {
			
			motivoTransferenciaViaturaRepository.findByIdIn(motivoTransferenciaIds)
					.forEach(m -> m.setAtivo(1));
		}
	}, 
	DESATIVAR {
		@Override
		public void executar(Long[] motivoTransferenciaIds,
				MotivoTransferenciaViaturaRepository motivoTransferenciaViaturaRepository) {

			motivoTransferenciaViaturaRepository.findByIdIn(motivoTransferenciaIds)
				.forEach(m -> m.setAtivo(0));
		}
	};
	
	
	public abstract void executar(Long[] motivoTransferenciaIds,
			MotivoTransferenciaViaturaRepository motivoTransferenciaViaturaRepository);


}

