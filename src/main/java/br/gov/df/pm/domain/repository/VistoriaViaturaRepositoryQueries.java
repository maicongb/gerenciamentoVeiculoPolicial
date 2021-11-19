package br.gov.df.pm.domain.repository;

import java.util.List;

import br.gov.df.pm.domain.model.VistoriaViatura;

public interface VistoriaViaturaRepositoryQueries {
	
	
	List<VistoriaViatura> verificaSeItemTipoVistoriaEmUso(Long tipoVistoriaId);
	

}
