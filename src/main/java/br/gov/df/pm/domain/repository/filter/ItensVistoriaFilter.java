package br.gov.df.pm.domain.repository.filter;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItensVistoriaFilter {
	
	private String nome;
	private Integer ativo;
	private Long tipoVistoriaId;

}
