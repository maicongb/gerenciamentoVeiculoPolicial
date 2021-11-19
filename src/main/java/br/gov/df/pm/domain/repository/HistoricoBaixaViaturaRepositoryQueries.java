package br.gov.df.pm.domain.repository;

import java.util.List;

import br.gov.df.pm.domain.model.HistoricoBaixaViatura;

public interface HistoricoBaixaViaturaRepositoryQueries {
	
	List<HistoricoBaixaViatura> verificaSeItemEmUso(Long motivoBaixaViaturaId);

}
