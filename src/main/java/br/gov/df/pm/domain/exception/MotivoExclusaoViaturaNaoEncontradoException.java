package br.gov.df.pm.domain.exception;

public class MotivoExclusaoViaturaNaoEncontradoException extends EntidadeNaoEncontradaException  {

	private static final long serialVersionUID = 1L;

	public MotivoExclusaoViaturaNaoEncontradoException(String mensagem) {
		super(mensagem);
	}
	
	public MotivoExclusaoViaturaNaoEncontradoException(Long tipoCombustivelId) {
		this(String.format("Não existe um cadastro de tipo de combustível com código %d", tipoCombustivelId));
	}

}
