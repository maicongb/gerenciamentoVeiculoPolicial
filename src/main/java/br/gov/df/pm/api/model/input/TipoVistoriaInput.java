package br.gov.df.pm.api.model.input;

import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TipoVistoriaInput {
	
	private Long id;
	
	@NotBlank
	private String nome;
	
	private Integer ativo;

}
