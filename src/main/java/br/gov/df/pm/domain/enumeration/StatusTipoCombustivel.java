package br.gov.df.pm.domain.enumeration;

import br.gov.df.pm.domain.repository.TipoCombustivelRepository;

public enum StatusTipoCombustivel {
	
	ATIVAR {
		@Override
		public void executar(Long[] tipoCombustivelIds,
				TipoCombustivelRepository tipoCombustivelRepository) {
			
			tipoCombustivelRepository.findByIdIn(tipoCombustivelIds)
					.forEach(m -> m.setAtivo(1));
			
			
		}
	}, 
	DESATIVAR {
		@Override
		public void executar(Long[] tipoCombustivelIds,
				TipoCombustivelRepository tipoCombustivelRepository) {

			tipoCombustivelRepository.findByIdIn(tipoCombustivelIds)
				.forEach(m -> m.setAtivo(0));
		}
	};
	
	
	public abstract void executar(Long[] tipoCombustivelIds,
			TipoCombustivelRepository tipoCombustivelRepository);


}
