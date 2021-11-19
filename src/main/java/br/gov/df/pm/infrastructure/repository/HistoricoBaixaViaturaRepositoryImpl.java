package br.gov.df.pm.infrastructure.repository;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.Predicate;

import org.springframework.stereotype.Repository;

import br.gov.df.pm.domain.model.HistoricoBaixaViatura;
import br.gov.df.pm.domain.repository.HistoricoBaixaViaturaRepositoryQueries;

@Repository
public class HistoricoBaixaViaturaRepositoryImpl implements HistoricoBaixaViaturaRepositoryQueries {
	
	@PersistenceContext
	private EntityManager manager;

	@Override
	public List<HistoricoBaixaViatura> verificaSeItemEmUso(Long motivoBaixaViaturaId) {
		var builder = manager.getCriteriaBuilder();	
		var criteria = builder.createQuery(HistoricoBaixaViatura.class);
		var root = criteria.from(HistoricoBaixaViatura.class);
		
		var predicates = new ArrayList<Predicate>();
	
		predicates.add(builder.equal(
						root.get("motivoBaixaViatura"), motivoBaixaViaturaId));
		
		criteria.where(predicates.toArray(new Predicate[0]));
		
		var query = manager.createQuery(criteria);
		return query.getResultList();
	}

}
