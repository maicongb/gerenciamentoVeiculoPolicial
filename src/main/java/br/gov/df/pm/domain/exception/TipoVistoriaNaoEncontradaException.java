package br.gov.df.pm.domain.exception;

public class TipoVistoriaNaoEncontradaException extends EntidadeNaoEncontradaException  {

	private static final long serialVersionUID = 1L;

	public TipoVistoriaNaoEncontradaException(String mensagem) {
		super(mensagem);
	}
	
	public TipoVistoriaNaoEncontradaException(Long tipoViaturaId) {
		this(String.format("Não existe um cadastro de tipo de viatura com código %d", tipoViaturaId));
	}

}
