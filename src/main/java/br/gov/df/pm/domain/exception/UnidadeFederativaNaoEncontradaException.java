package br.gov.df.pm.domain.exception;

public class UnidadeFederativaNaoEncontradaException extends EntidadeNaoEncontradaException  {

	private static final long serialVersionUID = 1L;

	public UnidadeFederativaNaoEncontradaException(String mensagem) {
		super(mensagem);
	}
	
	public UnidadeFederativaNaoEncontradaException(Long ufId) {
		this(String.format("Não existe o cadastro da UF com código %d", ufId));
	}

}
