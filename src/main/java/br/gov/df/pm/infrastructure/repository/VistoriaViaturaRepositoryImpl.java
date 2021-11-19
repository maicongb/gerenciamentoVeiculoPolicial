package br.gov.df.pm.infrastructure.repository;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.Predicate;

import org.springframework.stereotype.Repository;

import br.gov.df.pm.domain.model.VistoriaViatura;
import br.gov.df.pm.domain.repository.VistoriaViaturaRepositoryQueries;

@Repository
public class VistoriaViaturaRepositoryImpl implements VistoriaViaturaRepositoryQueries {
	
	@PersistenceContext
	private EntityManager manager;

	@Override
	public List<VistoriaViatura> verificaSeItemTipoVistoriaEmUso(Long tipoVistoriaId) {

		var builder = manager.getCriteriaBuilder();	
		var criteria = builder.createQuery(VistoriaViatura.class);
		var root = criteria.from(VistoriaViatura.class);
		
		var predicates = new ArrayList<Predicate>();
	
		predicates.add(builder.equal(root.get("tipoVistoria"), tipoVistoriaId));
		
		criteria.where(predicates.toArray(new Predicate[0]));
		
		var query = manager.createQuery(criteria);
		return query.getResultList();

	}
		

}
