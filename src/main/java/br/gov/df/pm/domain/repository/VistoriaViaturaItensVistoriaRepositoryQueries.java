package br.gov.df.pm.domain.repository;

import java.util.List;

import br.gov.df.pm.domain.model.VistoriaViaturaItensVistoria;

public interface VistoriaViaturaItensVistoriaRepositoryQueries {
	
	List<VistoriaViaturaItensVistoria> verificaSeItemVistoriaEmUso(Long itensVistoriaId);

}
