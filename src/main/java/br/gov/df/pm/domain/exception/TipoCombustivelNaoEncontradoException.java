package br.gov.df.pm.domain.exception;

public class TipoCombustivelNaoEncontradoException extends EntidadeNaoEncontradaException  {

	private static final long serialVersionUID = 1L;

	public TipoCombustivelNaoEncontradoException(String mensagem) {
		super(mensagem);
	}
	
	public TipoCombustivelNaoEncontradoException(Long tipoCombustivelId) {
		this(String.format("Não existe um cadastro de tipo de combustível com código %d", tipoCombustivelId));
	}

}
