package br.gov.df.pm.domain.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import br.gov.df.pm.domain.model.HistoricoBaixaViatura;

public interface HistoricoBaixaViaturaRepository extends 
					CustomJpaRepository<HistoricoBaixaViatura, Long>,
					HistoricoBaixaViaturaRepositoryQueries,
					JpaSpecificationExecutor<HistoricoBaixaViatura>{

	

}
