package br.gov.df.pm.domain.enumeration;

import br.gov.df.pm.domain.repository.ModeloVeiculoRepository;

public enum StatusModeloVeiculo {

	ATIVAR {
		@Override
		public void executar(Long[] modeloVeiculoIds,
				ModeloVeiculoRepository modeloVeiculoRepository) {
			
			modeloVeiculoRepository.findByIdIn(modeloVeiculoIds)
					.forEach(m -> m.setAtivo(1));
		}
	}, 
	DESATIVAR {
		@Override
		public void executar(Long[] modeloVeiculoIds,
				ModeloVeiculoRepository modeloVeiculoRepository) {

			modeloVeiculoRepository.findByIdIn(modeloVeiculoIds)
				.forEach(m -> m.setAtivo(0));
		}
	};
	
	
	public abstract void executar(Long[] modeloVeiculoIds,
			ModeloVeiculoRepository modeloVeiculoRepository);


}

