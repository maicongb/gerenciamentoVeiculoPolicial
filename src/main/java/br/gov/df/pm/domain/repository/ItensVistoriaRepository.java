package br.gov.df.pm.domain.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import br.gov.df.pm.domain.model.ItensVistoria;
import br.gov.df.pm.domain.model.TipoVistoria;

public interface ItensVistoriaRepository extends 
		CustomJpaRepository<ItensVistoria, Long>,
		JpaSpecificationExecutor<ItensVistoria>{

	public Optional<ItensVistoria> findByNomeAndTipoVistoria(String nome, TipoVistoria tipoVistoria);

	public List<ItensVistoria> findByIdIn(Long[] itensVistoriaIds);

	public List<ItensVistoria> findByAtivo(int i);

}


