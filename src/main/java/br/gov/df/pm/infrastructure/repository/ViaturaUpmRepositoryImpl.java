package br.gov.df.pm.infrastructure.repository;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.Predicate;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.gov.df.pm.domain.model.ViaturaUpm;
import br.gov.df.pm.domain.repository.ViaturaUpmRepositoryQueries;

@Repository
public class ViaturaUpmRepositoryImpl implements ViaturaUpmRepositoryQueries {
	
	@PersistenceContext
	private EntityManager manager;

	@Override
	public ViaturaUpm buscarUltimaMovimentacao(Long codigo) {
		
		var builder = manager.getCriteriaBuilder();	
		var criteria = builder.createQuery(ViaturaUpm.class);
		var root = criteria.from(ViaturaUpm.class);
		
		root.fetch("upm");
		
		var predicates = new ArrayList<Predicate>();
		
		predicates.add(builder.isNull(root.get("dataSaida")));
		predicates.add(builder.equal(root.get("viatura"), codigo));
		
		criteria.where(predicates.toArray(new Predicate[0]));
		
		var query = manager.createQuery(criteria);
		return query.getSingleResult();
	}

	@Transactional
	@Override
	public ViaturaUpm save(ViaturaUpm viaturaUpm) {
		return manager.merge(viaturaUpm);
	}

	@Override
	public List<ViaturaUpm> verificaSeItemMotivoExclusaoEmUso(Long motivoTransferenciaViaturaId) {
		
		var builder = manager.getCriteriaBuilder();	
		var criteria = builder.createQuery(ViaturaUpm.class);
		var root = criteria.from(ViaturaUpm.class);
		
		root.fetch("upm");
		
		var predicates = new ArrayList<Predicate>();
	
		Predicate motivoTransferenciaIgual = builder.equal(
						root.get("motivoTransferenciaViatura"), motivoTransferenciaViaturaId);
		
		Predicate viaturaUpmAtivoIgual = builder.equal(root.get("ativo"), 1);
		
		Predicate motivoTransferenciaIgualEviaturaUpmAtivoIgual = 
					builder.and(motivoTransferenciaIgual,viaturaUpmAtivoIgual);
		
		predicates.add(motivoTransferenciaIgualEviaturaUpmAtivoIgual);
		
		criteria.where(predicates.toArray(new Predicate[0]));
		
		var query = manager.createQuery(criteria);
		return query.getResultList();
	}

}
