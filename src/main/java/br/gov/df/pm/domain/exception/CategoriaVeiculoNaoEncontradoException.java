package br.gov.df.pm.domain.exception;

public class CategoriaVeiculoNaoEncontradoException extends EntidadeNaoEncontradaException  {

	private static final long serialVersionUID = 1L;

	public CategoriaVeiculoNaoEncontradoException(String mensagem) {
		super(mensagem);
	}
	
	public CategoriaVeiculoNaoEncontradoException(Long categoriaVeiculoId) {
		this(String.format("Não existe um cadastro de categoria de veículo com código %d", categoriaVeiculoId));
	}

}
