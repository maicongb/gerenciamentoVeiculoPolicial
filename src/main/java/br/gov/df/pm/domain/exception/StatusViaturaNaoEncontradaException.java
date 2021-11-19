package br.gov.df.pm.domain.exception;

public class StatusViaturaNaoEncontradaException extends EntidadeNaoEncontradaException  {

	private static final long serialVersionUID = 1L;

	public StatusViaturaNaoEncontradaException(String mensagem) {
		super(mensagem);
	}
	
	public StatusViaturaNaoEncontradaException(Long statusViaturaId) {
		this(String.format("Não existe um cadastro de status da viatura com código %d", statusViaturaId));
	}

}
