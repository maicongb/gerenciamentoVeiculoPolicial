package br.gov.df.pm.domain.exception;

public class TipoVeiculoNaoEncontradoException extends EntidadeNaoEncontradaException  {

	private static final long serialVersionUID = 1L;

	public TipoVeiculoNaoEncontradoException(String mensagem) {
		super(mensagem);
	}
	
	public TipoVeiculoNaoEncontradoException(Long tipoVeiculoId) {
		this(String.format("Não existe um cadastro de tipo de veículo com código %d", tipoVeiculoId));
	}

}
