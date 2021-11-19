package br.gov.df.pm.domain.enumeration;

import br.gov.df.pm.domain.repository.ItensVistoriaRepository;

public enum StatusItensVistoria {

	ATIVAR {
		@Override
		public void executar(Long[] itensVistoriaIds,
				ItensVistoriaRepository itensVistoriaRepository) {
			
			itensVistoriaRepository.findByIdIn(itensVistoriaIds)
					.forEach(m -> m.setAtivo(1));
		}
	}, 
	DESATIVAR {
		@Override
		public void executar(Long[] itensVistoriaIds,
				ItensVistoriaRepository itensVistoriaRepository) {

			itensVistoriaRepository.findByIdIn(itensVistoriaIds)
				.forEach(m -> m.setAtivo(0));
		}
	};
	
	
	public abstract void executar(Long[] itensVistoriaIds,
			ItensVistoriaRepository itensVistoriaRepository);


}

