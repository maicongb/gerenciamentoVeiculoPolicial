package br.gov.df.pm.domain.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import br.gov.df.pm.domain.model.MotivoTransferenciaViatura;

@Repository
public interface MotivoTransferenciaViaturaRepository extends 
					CustomJpaRepository<MotivoTransferenciaViatura, Long>,
					JpaSpecificationExecutor<MotivoTransferenciaViatura>{

	List<MotivoTransferenciaViatura> findByIdIn(Long[] motivoTransferenciaIds);
	
	Optional<MotivoTransferenciaViatura> findByNomeIgnoreCase(String nome);

}
