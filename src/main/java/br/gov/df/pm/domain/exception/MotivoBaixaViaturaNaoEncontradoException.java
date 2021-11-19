package br.gov.df.pm.domain.exception;

public class MotivoBaixaViaturaNaoEncontradoException extends EntidadeNaoEncontradaException  {

	private static final long serialVersionUID = 1L;

	public MotivoBaixaViaturaNaoEncontradoException(String mensagem) {
		super(mensagem);
	}
	
	public MotivoBaixaViaturaNaoEncontradoException(Long motivoBaixaViaturaId) {
		this(String.format("Não existe um cadastro de motivo da baixa da viatura com código %d", 
				motivoBaixaViaturaId));
	}

}
