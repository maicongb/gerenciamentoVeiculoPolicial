package br.gov.df.pm.api.model.input;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ModeloVeiculoInput {
	
	@NotBlank
	private String nome;
	
	@NotBlank
	private String potencia;
	
	@NotNull
	private Integer tanque;
	
	@NotNull
	private Integer importado;

	@Valid
	@NotNull
	private MarcaVeiculoIdInput marcaVeiculo;
	
	@Valid
	@NotNull
	private CategoriaVeiculoIdInput categoriaVeiculo;
	
}
