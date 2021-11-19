package br.gov.df.pm.domain.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import br.gov.df.pm.domain.model.MarcaVeiculo;
import br.gov.df.pm.domain.model.TipoVeiculo;

public interface MarcaVeiculoRepository extends 
		CustomJpaRepository<MarcaVeiculo, Long>,
		MarcaVeiculoRepositoryQueries,
		JpaSpecificationExecutor<MarcaVeiculo>{

	public Optional<MarcaVeiculo> findByNomeAndTipoVeiculo(String nome, TipoVeiculo tipoVeiculo);

	public List<MarcaVeiculo> findByIdIn(Long[] marcaVeiculoIds);

}


