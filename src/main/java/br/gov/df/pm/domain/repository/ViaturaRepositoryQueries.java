package br.gov.df.pm.domain.repository;

import java.util.List;
import java.util.Optional;

import br.gov.df.pm.domain.model.Viatura;

public interface ViaturaRepositoryQueries {
	
	Optional<Viatura> findById(String codigo);
	
	List<Viatura> verificaSeItemTipoViaturaEmUso(Long tipoViaturaId);
	
	List<Viatura> verificaSeItemMotivoExclusaoEmUso(Long motivoExclusaoViaturaId);
	
	List<Viatura> verificaSeItemTipoCombustivelEmUso(Long tipoCombustivelId);
}


