package br.gov.df.pm.domain.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import br.gov.df.pm.domain.model.TipoVeiculo;

public interface TipoVeiculoRepository extends 
		CustomJpaRepository<TipoVeiculo, Long>,
		JpaSpecificationExecutor<TipoVeiculo>{

	public List<TipoVeiculo> findByIdIn(Long[] codigos);

	public Optional<TipoVeiculo> findByNomeIgnoreCase(String nome);

	public List<TipoVeiculo> findByAtivoOrderByNome(int i);

}


