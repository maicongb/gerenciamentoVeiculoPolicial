package br.gov.df.pm.domain.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import br.gov.df.pm.domain.model.TipoCombustivel;

public interface TipoCombustivelRepository extends 
		CustomJpaRepository<TipoCombustivel, Long>,
		JpaSpecificationExecutor<TipoCombustivel>{

	public List<TipoCombustivel> findByIdIn(Long[] codigos);

	public Optional<TipoCombustivel> findByNomeIgnoreCase(String nome);

}


