package br.gov.df.pm.domain.exception;

public class UnidadePolicialMilitarNaoEncontradaException extends EntidadeNaoEncontradaException  {

	private static final long serialVersionUID = 1L;

	public UnidadePolicialMilitarNaoEncontradaException(String mensagem) {
		super(mensagem);
	}
	
	public UnidadePolicialMilitarNaoEncontradaException(Long upmId) {
		this(String.format("Não existe o cadastro da UPM com código %d", upmId));
	}

}
