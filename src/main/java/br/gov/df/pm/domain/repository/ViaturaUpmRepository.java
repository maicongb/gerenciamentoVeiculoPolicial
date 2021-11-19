package br.gov.df.pm.domain.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import br.gov.df.pm.domain.model.ViaturaUpm;


public interface ViaturaUpmRepository extends 
		CustomJpaRepository<ViaturaUpm, Long>, ViaturaUpmRepositoryQueries,
		JpaSpecificationExecutor<ViaturaUpm>{

}


