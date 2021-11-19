package br.gov.df.pm.domain.repository;

import java.util.List;

import br.gov.df.pm.domain.model.ViaturaUpm;

public interface ViaturaUpmRepositoryQueries {
	
	ViaturaUpm buscarUltimaMovimentacao(Long viaturaId);
	
	ViaturaUpm save(ViaturaUpm viaturaUpm);
	
	List<ViaturaUpm> verificaSeItemMotivoExclusaoEmUso(Long motivoTransferenciaViaturaId);

}
