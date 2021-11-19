package br.gov.df.pm.domain.exception;

public class ItensVistoriaNaoEncontradoException extends EntidadeNaoEncontradaException  {

	private static final long serialVersionUID = 1L;

	public ItensVistoriaNaoEncontradoException(String mensagem) {
		super(mensagem);
	}
	
	public ItensVistoriaNaoEncontradoException(Long itensVistoriaId) {
		this(String.format("Não existe um cadastro de tipo de item com código %d", itensVistoriaId));
	}

}
