package br.gov.df.pm.domain.enumeration;

import br.gov.df.pm.domain.repository.CategoriaVeiculoRepository;

public enum StatusCategoriaVeiculo {

	ATIVAR {
		@Override
		public void executar(Long[] categoriaVeiculoIds,
				CategoriaVeiculoRepository categoriaVeiculoRepository) {
			
			categoriaVeiculoRepository.findByIdIn(categoriaVeiculoIds)
					.forEach(m -> m.setAtivo(1));
		}
	}, 
	DESATIVAR {
		@Override
		public void executar(Long[] categoriaVeiculoIds,
				CategoriaVeiculoRepository categoriaVeiculoRepository) {

			categoriaVeiculoRepository.findByIdIn(categoriaVeiculoIds)
				.forEach(m -> m.setAtivo(0));
		}
	};
	
	
	public abstract void executar(Long[] categoriaVeiculoIds,
			CategoriaVeiculoRepository categoriaVeiculoRepository);


}

