package br.gov.df.pm.infrastructure.repository;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.Predicate;

import org.springframework.stereotype.Repository;

import br.gov.df.pm.domain.model.ModeloVeiculo;
import br.gov.df.pm.domain.repository.ModeloVeiculoRepositoryQueries;

@Repository
public class ModeloVeiculoRepositoryImpl implements ModeloVeiculoRepositoryQueries {
	
	@PersistenceContext
	private EntityManager manager;

	@Override
	public List<ModeloVeiculo> verificaSeItemEmUso(Long categoriaVeiculoId) {
		
		var builder = manager.getCriteriaBuilder();	
		var criteria = builder.createQuery(ModeloVeiculo.class);
		var root = criteria.from(ModeloVeiculo.class);
		
		var predicates = new ArrayList<Predicate>();
	
		Predicate categoriaVeiculoIgual = builder.equal(root.get("categoriaVeiculo"), categoriaVeiculoId);
		Predicate modeloVeiculoIgual = builder.equal(root.get("ativo"), 1);
		
		Predicate categoriaVeiculoIgualEmodeloVeiculoIgual = 
					builder.and(categoriaVeiculoIgual,modeloVeiculoIgual);
		
		predicates.add(categoriaVeiculoIgualEmodeloVeiculoIgual);
		
		criteria.where(predicates.toArray(new Predicate[0]));
		
		var query = manager.createQuery(criteria);
		return query.getResultList();

	}

}
