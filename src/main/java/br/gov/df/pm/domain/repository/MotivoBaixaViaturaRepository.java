package br.gov.df.pm.domain.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import br.gov.df.pm.domain.model.MotivoBaixaViatura;

@Repository
public interface MotivoBaixaViaturaRepository extends CustomJpaRepository<MotivoBaixaViatura, Long>,
			JpaSpecificationExecutor<MotivoBaixaViatura>{

	Optional<MotivoBaixaViatura> findByNomeIgnoreCase(String nome);

	List<MotivoBaixaViatura> findByIdIn(Long[] motivoTransferenciaIds);

}
