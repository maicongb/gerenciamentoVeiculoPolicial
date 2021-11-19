package br.gov.df.pm.domain.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import br.gov.df.pm.domain.model.VistoriaViatura;

public interface VistoriaViaturaRepository extends 
		CustomJpaRepository<VistoriaViatura, Long>,
		VistoriaViaturaRepositoryQueries,
		JpaSpecificationExecutor<VistoriaViatura>{


}


