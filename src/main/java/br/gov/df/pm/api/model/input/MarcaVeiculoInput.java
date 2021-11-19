package br.gov.df.pm.api.model.input;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MarcaVeiculoInput {
	
	@NotBlank
	private String nome;
	@NotBlank
	private String abreviacao;
	
	@Valid
	@NotNull
	private TipoVeiculoIdInput tipoVeiculo;

}
