package br.gov.df.pm.domain.exception;

public class MarcaVeiculoNaoEncontradaException extends EntidadeNaoEncontradaException  {

	private static final long serialVersionUID = 1L;

	public MarcaVeiculoNaoEncontradaException(String mensagem) {
		super(mensagem);
	}
	
	public MarcaVeiculoNaoEncontradaException(Long marcaVeiculoId) {
		this(String.format("Não existe um cadastro de marca de veículo com código %d", marcaVeiculoId));
	}

}
