package br.gov.df.pm.infrastructure.repository.spec;

import java.util.ArrayList;

import javax.persistence.criteria.Predicate;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import br.gov.df.pm.domain.model.ModeloVeiculo;
import br.gov.df.pm.domain.repository.filter.ModeloVeiculoFilter;

public class ModeloVeiculoSpecs {
	
	public static Specification<ModeloVeiculo> usandoFiltro(ModeloVeiculoFilter filtro) {
		return (root, query, builder) -> {
			if(ModeloVeiculo.class.equals(query.getResultType())){
				root.fetch("marcaVeiculo").fetch("tipoVeiculo");
				root.fetch("categoriaVeiculo");
			}
			
			var predicates = new ArrayList<Predicate>();
			
			if (!StringUtils.isEmpty(filtro.getNome())) {
				predicates.add(builder.like(root.get("nome"), "%"+filtro.getNome()+"%" ));
			}
			
			if (filtro.getAtivo() != null) {
				predicates.add(builder.equal(root.get("ativo"), filtro.getAtivo()));
			}
			
			if (filtro.getMarcaVeiculoId() != null) {
				predicates.add(builder.equal(root.get("marcaVeiculo"), 
						filtro.getMarcaVeiculoId()));
			}
			
			if (filtro.getCategoriaVeiculoId() != null) {
				predicates.add(builder.equal(root.get("categoriaVeiculo"), 
						filtro.getCategoriaVeiculoId()));
			}
			
			return builder.and(predicates.toArray(new Predicate[0]));
		};
	}

}
