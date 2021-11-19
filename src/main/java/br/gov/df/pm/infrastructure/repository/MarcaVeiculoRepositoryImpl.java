package br.gov.df.pm.infrastructure.repository;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.Predicate;

import org.springframework.stereotype.Repository;

import br.gov.df.pm.domain.model.MarcaVeiculo;
import br.gov.df.pm.domain.repository.MarcaVeiculoRepositoryQueries;

@Repository
public class MarcaVeiculoRepositoryImpl implements MarcaVeiculoRepositoryQueries {

	@PersistenceContext
	private EntityManager manager;
	
	@Override
	public List<MarcaVeiculo> verificaSeItemEmUso(Long tipoVeiculoId) {
		
		var builder = manager.getCriteriaBuilder();	
		var criteria = builder.createQuery(MarcaVeiculo.class);
		var root = criteria.from(MarcaVeiculo.class);
		
		var predicates = new ArrayList<Predicate>();
	
		Predicate tipoVeiculoIgual = builder.equal(root.get("tipoVeiculo"), tipoVeiculoId);
		Predicate marcaVeiculoIgual = builder.equal(root.get("ativo"), 1);
		
		Predicate tipoVeiculoIgualEmarcaVeiculoIgual = 
					builder.and(tipoVeiculoIgual,marcaVeiculoIgual);
		
		predicates.add(tipoVeiculoIgualEmarcaVeiculoIgual);
		
		criteria.where(predicates.toArray(new Predicate[0]));
		
		var query = manager.createQuery(criteria);
		return query.getResultList();

	}

}
