package br.gov.df.pm.domain.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import br.gov.df.pm.domain.model.TipoVistoria;

public interface TipoVistoriaRepository extends 
		CustomJpaRepository<TipoVistoria, Long>,
		JpaSpecificationExecutor<TipoVistoria>{

	public List<TipoVistoria> findByIdIn(Long[] tipoVistoriaIds);

	public Optional<TipoVistoria> findByNomeIgnoreCase(String nome);

	public List<TipoVistoria> findByAtivo(int i);

}


