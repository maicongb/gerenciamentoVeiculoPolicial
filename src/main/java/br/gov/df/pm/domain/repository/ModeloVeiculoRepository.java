package br.gov.df.pm.domain.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import br.gov.df.pm.domain.model.MarcaVeiculo;
import br.gov.df.pm.domain.model.ModeloVeiculo;

public interface ModeloVeiculoRepository extends 
		CustomJpaRepository<ModeloVeiculo, Long>,
		ModeloVeiculoRepositoryQueries,
		JpaSpecificationExecutor<ModeloVeiculo>{

	public List<ModeloVeiculo> findByIdIn(Long[] modeloVeiculoIds);

	public Optional<ModeloVeiculo> findByNomeAndMarcaVeiculo(String nome, MarcaVeiculo marcaVeiculo);

}


