package br.gov.df.pm.domain.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import br.gov.df.pm.domain.model.TipoViatura;

public interface TipoViaturaRepository extends 
		CustomJpaRepository<TipoViatura, Long>,
		JpaSpecificationExecutor<TipoViatura>{

	public List<TipoViatura> findByIdIn(Long[] codigos);

	public Optional<TipoViatura> findByNomeIgnoreCase(String nome);

}


