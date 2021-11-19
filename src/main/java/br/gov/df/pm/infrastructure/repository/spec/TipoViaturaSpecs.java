package br.gov.df.pm.infrastructure.repository.spec;

import java.util.ArrayList;

import javax.persistence.criteria.Predicate;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import br.gov.df.pm.domain.model.TipoViatura;
import br.gov.df.pm.domain.repository.filter.TipoViaturaFilter;

public class TipoViaturaSpecs {
	
	public static Specification<TipoViatura> usandoFiltro(TipoViaturaFilter filtro) {
		return (root, query, builder) -> {
			
			var predicates = new ArrayList<Predicate>();
			
			if(filtro != null) {
				if (!StringUtils.isEmpty(filtro.getNome())) {
					predicates.add(builder.like(root.get("nome"), "%"+filtro.getNome()+"%" ));
				}
				
				if (filtro.getAtivo() != null) {
					predicates.add(builder.equal(root.get("ativo"), filtro.getAtivo()));
				}
			}	
			
			return builder.and(predicates.toArray(new Predicate[0]));
		};
	}

}
