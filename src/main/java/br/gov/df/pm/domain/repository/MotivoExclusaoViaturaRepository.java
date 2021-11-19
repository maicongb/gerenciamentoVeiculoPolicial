package br.gov.df.pm.domain.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import br.gov.df.pm.domain.model.MotivoExclusaoViatura;

@Repository
public interface MotivoExclusaoViaturaRepository extends CustomJpaRepository<MotivoExclusaoViatura, Long>,
			JpaSpecificationExecutor<MotivoExclusaoViatura>{

	Optional<MotivoExclusaoViatura> findByNomeIgnoreCase(String nome);

	List<MotivoExclusaoViatura> findByIdIn(Long[] motivoExclusaoViaturaIds);

}
