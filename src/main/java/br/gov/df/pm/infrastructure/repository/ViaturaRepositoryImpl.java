package br.gov.df.pm.infrastructure.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;

import org.springframework.stereotype.Repository;

import br.gov.df.pm.domain.model.Viatura;
import br.gov.df.pm.domain.model.ViaturaUpm;
import br.gov.df.pm.domain.repository.ViaturaRepositoryQueries;

@Repository
public class ViaturaRepositoryImpl implements ViaturaRepositoryQueries {
	
	@PersistenceContext
	private EntityManager manager;

	@Override
	public Optional<Viatura> findById(String codigo) {
		
		var builder = manager.getCriteriaBuilder();	
		var criteria = builder.createQuery(Viatura.class);
		var root = criteria.from(Viatura.class);
		
		root.fetch("modeloVeiculo").fetch("marcaVeiculo").fetch("tipoVeiculo");
		root.fetch("modeloVeiculo").fetch("categoriaVeiculo");
		root.fetch("ufPlaca");
		root.fetch("tipoCombustivel");
		root.fetch("tipoViatura");
		root.fetch("statusViatura");
		root.fetch("motivoExclusao", JoinType.LEFT);
		root.fetch("placaVinculada", JoinType.LEFT);
		root.fetch("viaturasUpm").fetch("upm");
			
		var predicates = new ArrayList<Predicate>();
		
		predicates.add(builder.equal(root.get("id"), codigo));
		
		Join<Viatura, ViaturaUpm> viaturaJoinViaturaUpm = root.join("viaturasUpm");
		predicates.add(builder.isNull(viaturaJoinViaturaUpm.get("dataSaida")));
		
		criteria.where(predicates.toArray(new Predicate[0]));
		
		var query = manager.createQuery(criteria);
		return Optional.ofNullable(query.getSingleResult());
	}

	@Override
	public List<Viatura> verificaSeItemTipoViaturaEmUso(Long tipoViaturaId) {
		
		var builder = manager.getCriteriaBuilder();	
		var criteria = builder.createQuery(Viatura.class);
		var root = criteria.from(Viatura.class);
		
		root.fetch("modeloVeiculo").fetch("marcaVeiculo").fetch("tipoVeiculo");
		root.fetch("modeloVeiculo").fetch("categoriaVeiculo");
		root.fetch("ufPlaca");
		root.fetch("tipoCombustivel");
		root.fetch("tipoViatura");
		root.fetch("statusViatura");
		root.fetch("motivoExclusao", JoinType.LEFT);
		root.fetch("placaVinculada", JoinType.LEFT);
		root.fetch("viaturasUpm").fetch("upm");
		
		var predicates = new ArrayList<Predicate>();
	
		Predicate tipoViaturaIgual = builder.equal(root.get("tipoViatura"), tipoViaturaId);
		Predicate viaturaIgual = builder.equal(root.get("ativo"), 1);
		
		Predicate tipoViaturaIgualEviaturaIgual = 
					builder.and(tipoViaturaIgual,viaturaIgual);
		
		predicates.add(tipoViaturaIgualEviaturaIgual);
		
		criteria.where(predicates.toArray(new Predicate[0]));
		
		var query = manager.createQuery(criteria);
		return query.getResultList();
	}

	@Override
	public List<Viatura> verificaSeItemMotivoExclusaoEmUso(Long motivoExclusaoViaturaId) {
		
		var builder = manager.getCriteriaBuilder();	
		var criteria = builder.createQuery(Viatura.class);
		var root = criteria.from(Viatura.class);
		
		root.fetch("modeloVeiculo").fetch("marcaVeiculo").fetch("tipoVeiculo");
		root.fetch("modeloVeiculo").fetch("categoriaVeiculo");
		root.fetch("ufPlaca");
		root.fetch("tipoCombustivel");
		root.fetch("tipoViatura");
		root.fetch("statusViatura");
		root.fetch("motivoExclusao", JoinType.LEFT);
		root.fetch("placaVinculada", JoinType.LEFT);
		root.fetch("viaturasUpm").fetch("upm");
		
		var predicates = new ArrayList<Predicate>();
	
		Predicate motivoExclusaoViaturaIgual = builder.equal(
							root.get("motivoExclusao"), motivoExclusaoViaturaId);
		
		Predicate viaturaIgual = builder.equal(root.get("ativo"), 1);
		
		Predicate motivoExclusaoViaturaIgualEviaturaIgual = 
					builder.and(motivoExclusaoViaturaIgual,viaturaIgual);
		
		predicates.add(motivoExclusaoViaturaIgualEviaturaIgual);
		
		criteria.where(predicates.toArray(new Predicate[0]));
		
		var query = manager.createQuery(criteria);
		return query.getResultList();
	}

	@Override
	public List<Viatura> verificaSeItemTipoCombustivelEmUso(Long tipoCombustivelId) {
		
		var builder = manager.getCriteriaBuilder();	
		var criteria = builder.createQuery(Viatura.class);
		var root = criteria.from(Viatura.class);
		
		root.fetch("modeloVeiculo").fetch("marcaVeiculo").fetch("tipoVeiculo");
		root.fetch("modeloVeiculo").fetch("categoriaVeiculo");
		root.fetch("ufPlaca");
		root.fetch("tipoCombustivel");
		root.fetch("tipoViatura");
		root.fetch("statusViatura");
		root.fetch("motivoExclusao", JoinType.LEFT);
		root.fetch("placaVinculada", JoinType.LEFT);
		root.fetch("viaturasUpm").fetch("upm");
		
		var predicates = new ArrayList<Predicate>();
	
		Predicate tipoCombustivelIgual = builder.equal(
							root.get("tipoCombustivel"), tipoCombustivelId);
		
		Predicate viaturaIgual = builder.equal(root.get("ativo"), 1);
		
		Predicate tipoCombustivelIgualEviaturaIgual = 
					builder.and(tipoCombustivelIgual,viaturaIgual);
		
		predicates.add(tipoCombustivelIgualEviaturaIgual);
		
		criteria.where(predicates.toArray(new Predicate[0]));
		
		var query = manager.createQuery(criteria);
		
		return query.getResultList();
	}
	
	

}
