package br.gov.df.pm.domain.enumeration;

import br.gov.df.pm.domain.repository.MotivoBaixaViaturaRepository;

public enum StatusMotivoBaixaViatura {

	ATIVAR {
		@Override
		public void executar(Long[] motivoBaixaViaturaIds,
				MotivoBaixaViaturaRepository motivoBaixaViaturaRepository) {
			
			motivoBaixaViaturaRepository.findByIdIn(motivoBaixaViaturaIds)
					.forEach(m -> m.setAtivo(1));
		}
	}, 
	DESATIVAR {
		@Override
		public void executar(Long[] motivoBaixaIds,
				MotivoBaixaViaturaRepository motivoBaixaViaturaRepository) {

			motivoBaixaViaturaRepository.findByIdIn(motivoBaixaIds)
				.forEach(m -> m.setAtivo(0));
		}
	};
	
	
	public abstract void executar(Long[] motivoTransferenciaIds,
			MotivoBaixaViaturaRepository motivoBaixaViaturaRepository);


}

