package br.gov.df.pm.api.model.input;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItensVistoriaInput {
	
	private Long id;
	
	@NotBlank
	private String nome;
	
	private Integer ativo;
	
	@Valid
	@NotNull
	private TipoVistoriaIdInput tipoVistoria;
	


}
