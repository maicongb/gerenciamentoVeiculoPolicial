package br.gov.df.pm.domain.exception;

public class ModeloVeiculoNaoEncontradoException extends EntidadeNaoEncontradaException  {

	private static final long serialVersionUID = 1L;

	public ModeloVeiculoNaoEncontradoException(String mensagem) {
		super(mensagem);
	}
	
	public ModeloVeiculoNaoEncontradoException(Long modeloVeiculoId) {
		this(String.format("Não existe um cadastro de modelo de veículo com código %d", modeloVeiculoId));
	}

}
