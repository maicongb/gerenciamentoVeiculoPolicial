package br.gov.df.pm.domain.enumeration;

import br.gov.df.pm.domain.repository.MarcaVeiculoRepository;

public enum StatusMarcaVeiculo {

	ATIVAR {
		@Override
		public void executar(Long[] marcaVeiculoIds,
				MarcaVeiculoRepository marcaVeiculoRepository) {
			
			marcaVeiculoRepository.findByIdIn(marcaVeiculoIds)
					.forEach(m -> m.setAtivo(1));
		}
	}, 
	DESATIVAR {
		@Override
		public void executar(Long[] marcaVeiculoIds,
				MarcaVeiculoRepository marcaVeiculoRepository) {

			marcaVeiculoRepository.findByIdIn(marcaVeiculoIds)
				.forEach(m -> m.setAtivo(0));
		}
	};
	
	
	public abstract void executar(Long[] marcaVeiculoIds,
			MarcaVeiculoRepository marcaVeiculoRepository);


}

