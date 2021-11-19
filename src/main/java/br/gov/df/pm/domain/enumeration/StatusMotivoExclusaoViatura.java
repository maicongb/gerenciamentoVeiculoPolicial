package br.gov.df.pm.domain.enumeration;

import br.gov.df.pm.domain.repository.MotivoExclusaoViaturaRepository;

public enum StatusMotivoExclusaoViatura {

	ATIVAR {
		@Override
		public void executar(Long[] motivoExclusaoViaturaIds,
				MotivoExclusaoViaturaRepository motivoExclusaoViaturaRepository) {
			
			motivoExclusaoViaturaRepository.findByIdIn(motivoExclusaoViaturaIds)
					.forEach(m -> m.setAtivo(1));
		}
	}, 
	DESATIVAR {
		@Override
		public void executar(Long[] motivoExclusaoIds,
				MotivoExclusaoViaturaRepository motivoExclusaoViaturaRepository) {

			motivoExclusaoViaturaRepository.findByIdIn(motivoExclusaoIds)
				.forEach(m -> m.setAtivo(0));
		}
	};
	
	
	public abstract void executar(Long[] motivoExclusaoIds,
			MotivoExclusaoViaturaRepository motivoExclusaoViaturaRepository);


}

