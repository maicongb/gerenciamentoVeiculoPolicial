package br.gov.df.pm.domain.exception;

public class TipoViaturaNaoEncontradoException extends EntidadeNaoEncontradaException  {

	private static final long serialVersionUID = 1L;

	public TipoViaturaNaoEncontradoException(String mensagem) {
		super(mensagem);
	}
	
	public TipoViaturaNaoEncontradoException(Long tipoViaturaId) {
		this(String.format("Não existe um cadastro de tipo de viatura com código %d", tipoViaturaId));
	}

}
