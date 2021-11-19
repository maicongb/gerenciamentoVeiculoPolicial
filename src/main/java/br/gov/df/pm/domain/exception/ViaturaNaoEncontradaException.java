package br.gov.df.pm.domain.exception;

public class ViaturaNaoEncontradaException extends EntidadeNaoEncontradaException  {

	private static final long serialVersionUID = 1L;

	public ViaturaNaoEncontradaException(String viaturaId) {
		super(String.format("Não existe um cadastro viatura com código %s", viaturaId));
	}

}
