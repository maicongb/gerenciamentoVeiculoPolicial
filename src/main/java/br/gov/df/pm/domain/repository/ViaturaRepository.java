package br.gov.df.pm.domain.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import br.gov.df.pm.domain.model.Viatura;

public interface ViaturaRepository extends 
		CustomJpaRepository<Viatura, Long>,
		ViaturaRepositoryQueries,
		JpaSpecificationExecutor<Viatura>{

	Optional<Viatura> findByPrefixoIgnoreCase(String prefixo);

	Optional<Viatura> findByPlacaIgnoreCase(String placa);

	Optional<Viatura> findByChassiIgnoreCase(String chassi);

	Optional<Viatura> findByNumeroMotorIgnoreCase(String numeroMotor);

	Optional<Viatura> findByRenavamIgnoreCase(String renavam);

	Optional<Viatura> findByTombamentoIgnoreCase(String tombamento);

}


