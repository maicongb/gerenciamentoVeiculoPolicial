package br.gov.df.pm.domain.repository;

import java.util.List;

import br.gov.df.pm.domain.model.MarcaVeiculo;

public interface MarcaVeiculoRepositoryQueries {
	
	public List<MarcaVeiculo> verificaSeItemEmUso(Long tipoVeiculoId);

}
