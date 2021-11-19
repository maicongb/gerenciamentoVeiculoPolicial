package br.gov.df.pm.domain.enumeration;

import br.gov.df.pm.domain.repository.TipoVistoriaRepository;

public enum StatusTipoVistoria {

	ATIVAR {
		@Override
		public void executar(Long[] tipoVistoriaIds,
				TipoVistoriaRepository tipoVistoriaRepository) {
			
			tipoVistoriaRepository.findByIdIn(tipoVistoriaIds)
					.forEach(m -> m.setAtivo(1));
		}
	}, 
	DESATIVAR {
		@Override
		public void executar(Long[] tipoVistoriaIds,
				TipoVistoriaRepository tipoVistoriaRepository) {

			tipoVistoriaRepository.findByIdIn(tipoVistoriaIds)
				.forEach(m -> m.setAtivo(0));
		}
	};
	
	
	public abstract void executar(Long[] tipoVistoriaIds,
			TipoVistoriaRepository tipoVistoriaRepository);


}

