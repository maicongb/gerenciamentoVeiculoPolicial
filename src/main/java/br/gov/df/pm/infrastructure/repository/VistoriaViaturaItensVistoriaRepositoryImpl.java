package br.gov.df.pm.infrastructure.repository;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.Predicate;

import org.springframework.stereotype.Repository;

import br.gov.df.pm.domain.model.VistoriaViaturaItensVistoria;
import br.gov.df.pm.domain.repository.VistoriaViaturaItensVistoriaRepositoryQueries;


@Repository
public class VistoriaViaturaItensVistoriaRepositoryImpl 
				implements VistoriaViaturaItensVistoriaRepositoryQueries{
	
	@PersistenceContext
	private EntityManager manager;

	@Override
	public List<VistoriaViaturaItensVistoria> verificaSeItemVistoriaEmUso(Long itensVistoriaId) {
		
		var builder = manager.getCriteriaBuilder();	
		var criteria = builder.createQuery(VistoriaViaturaItensVistoria.class);
		var root = criteria.from(VistoriaViaturaItensVistoria.class);
		
		var predicates = new ArrayList<Predicate>();
	
		predicates.add(builder.equal(root.get("itensVistoria"), itensVistoriaId));
		
		criteria.where(predicates.toArray(new Predicate[0]));
		
		var query = manager.createQuery(criteria);
		return query.getResultList();

	}

}
