package br.gov.df.pm.infrastructure.repository.spec;

import java.util.ArrayList;

import javax.persistence.criteria.Predicate;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import br.gov.df.pm.domain.model.ItensVistoria;
import br.gov.df.pm.domain.repository.filter.ItensVistoriaFilter;

public class ItensVistoriaSpecs {
	
	public static Specification<ItensVistoria> usandoFiltro(ItensVistoriaFilter filtro) {
		return (root, query, builder) -> {
			if(ItensVistoria.class.equals(query.getResultType())){
				root.fetch("tipoVistoria");
			}
			
			var predicates = new ArrayList<Predicate>();
			
			if(filtro != null) {
				if (!StringUtils.isEmpty(filtro.getNome())) {
					predicates.add(builder.like(root.get("nome"), "%"+filtro.getNome()+"%" ));
				}
				
				if (filtro.getAtivo() != null) {
					predicates.add(builder.equal(root.get("ativo"), filtro.getAtivo()));
				}
				
				if (filtro.getTipoVistoriaId() != null) {
					predicates.add(builder.equal(root.get("tipoVistoria"), filtro.getTipoVistoriaId()));
				}
			}	
			
			return builder.and(predicates.toArray(new Predicate[0]));
		};
	}

}
