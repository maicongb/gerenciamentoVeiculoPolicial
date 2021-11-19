package br.gov.df.pm.domain.enumeration;

import br.gov.df.pm.domain.repository.TipoVeiculoRepository;

public enum StatusTipoVeiculo {

	ATIVAR {
		@Override
		public void executar(Long[] tipoVeiculoIds,
				TipoVeiculoRepository tipoVeiculoRepository) {
			
			tipoVeiculoRepository.findByIdIn(tipoVeiculoIds)
					.forEach(m -> m.setAtivo(1));
		}
	}, 
	DESATIVAR {
		@Override
		public void executar(Long[] tipoVeiculoIds,
				TipoVeiculoRepository tipoVeiculoRepository) {

			tipoVeiculoRepository.findByIdIn(tipoVeiculoIds)
				.forEach(m -> m.setAtivo(0));
		}
	};
	
	
	public abstract void executar(Long[] tipoVeiculoIds,
			TipoVeiculoRepository tipoVeiculoRepository);


}

