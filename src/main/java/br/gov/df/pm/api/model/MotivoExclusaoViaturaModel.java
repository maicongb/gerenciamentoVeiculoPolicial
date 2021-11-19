package br.gov.df.pm.api.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MotivoExclusaoViaturaModel {

	private Long id;
	private String nome;
	private Integer ativo;
}
