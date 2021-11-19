package br.gov.df.pm.infrastructure.repository.spec;

import java.util.ArrayList;

import javax.persistence.criteria.Predicate;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import br.gov.df.pm.domain.model.MarcaVeiculo;
import br.gov.df.pm.domain.repository.filter.MarcaVeiculoFilter;

public class MarcaVeiculoSpecs {
	
	public static Specification<MarcaVeiculo> usandoFiltro(MarcaVeiculoFilter filtro) {
		return (root, query, builder) -> {
			if(MarcaVeiculo.class.equals(query.getResultType())){
				root.fetch("tipoVeiculo");
			}
			
			var predicates = new ArrayList<Predicate>();
			
			if (!StringUtils.isEmpty(filtro.getNome())) {
				predicates.add(builder.like(root.get("nome"), "%"+filtro.getNome()+"%" ));
			}
			
			if (filtro.getAtivo() != null) {
				predicates.add(builder.equal(root.get("ativo"), filtro.getAtivo()));
			}
			
			if (filtro.getTipoVeiculoId() != null) {
				predicates.add(builder.equal(root.get("tipoVeiculo"), filtro.getTipoVeiculoId()));
			}
			
			return builder.and(predicates.toArray(new Predicate[0]));
		};
	}

}
