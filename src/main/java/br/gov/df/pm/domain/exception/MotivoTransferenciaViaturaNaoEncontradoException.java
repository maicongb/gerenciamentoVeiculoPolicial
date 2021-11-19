package br.gov.df.pm.domain.exception;

public class MotivoTransferenciaViaturaNaoEncontradoException extends EntidadeNaoEncontradaException  {

	private static final long serialVersionUID = 1L;

	public MotivoTransferenciaViaturaNaoEncontradoException(String mensagem) {
		super(mensagem);
	}
	
	public MotivoTransferenciaViaturaNaoEncontradoException(Long motivoId) {
		this(String.format("Não existe um cadastro de motivo de "
				+ "transferência de viatura com código %d", motivoId));
	}
}