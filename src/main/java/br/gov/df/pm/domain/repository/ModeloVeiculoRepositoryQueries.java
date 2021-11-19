package br.gov.df.pm.domain.repository;

import java.util.List;

import br.gov.df.pm.domain.model.ModeloVeiculo;

public interface ModeloVeiculoRepositoryQueries {
	
	public List<ModeloVeiculo> verificaSeItemEmUso(Long categoriaVeiculoId);

}
