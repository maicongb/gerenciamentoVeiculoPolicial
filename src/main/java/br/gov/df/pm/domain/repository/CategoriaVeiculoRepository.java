package br.gov.df.pm.domain.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import br.gov.df.pm.domain.model.CategoriaVeiculo;

public interface CategoriaVeiculoRepository extends 
		CustomJpaRepository<CategoriaVeiculo, Long>,
		JpaSpecificationExecutor<CategoriaVeiculo>{

	public List<CategoriaVeiculo> findByIdIn(Long[] codigos);

	public Optional<CategoriaVeiculo> findByNomeIgnoreCase(String nome);

}


