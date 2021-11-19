package br.gov.df.pm.domain.enumeration;

import br.gov.df.pm.domain.repository.TipoViaturaRepository;

public enum StatusTipoViatura {

	ATIVAR {
		@Override
		public void executar(Long[] tipoViaturaIds,
				TipoViaturaRepository tipoViaturaRepository) {
			
			tipoViaturaRepository.findByIdIn(tipoViaturaIds)
					.forEach(m -> m.setAtivo(1));
		}
	}, 
	DESATIVAR {
		@Override
		public void executar(Long[] tipoViaturaIds,
				TipoViaturaRepository tipoViaturaRepository) {

			tipoViaturaRepository.findByIdIn(tipoViaturaIds)
				.forEach(m -> m.setAtivo(0));
		}
	};
	
	
	public abstract void executar(Long[] tipoViaturaIds,
			TipoViaturaRepository tipoViaturaRepository);


}

