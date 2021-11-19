package br.gov.df.pm.domain.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import br.gov.df.pm.domain.model.VistoriaViaturaItensVistoria;

public interface VistoriaViaturaItensVistoriaRepository extends 
							CustomJpaRepository<VistoriaViaturaItensVistoria, Long>,
							VistoriaViaturaItensVistoriaRepositoryQueries,
							JpaSpecificationExecutor<VistoriaViaturaItensVistoria>{

	

}
