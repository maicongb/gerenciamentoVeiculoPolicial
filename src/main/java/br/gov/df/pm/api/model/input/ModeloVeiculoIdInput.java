package br.gov.df.pm.api.model.input;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ModeloVeiculoIdInput {

	@NotNull
	private Long id;
}
