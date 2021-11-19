package br.gov.df.pm.domain.enumeration;

public enum StatusViaturaEnum {
	
	CAUTELADA(1L, "CAUTELADA"),
	DISPONÍVEL(2L, "DISPONÍVEL"),
	BAIXADA(3L, "BAIXADA"),
	EMVISTORIA(4L, "EM VISTORIA"),
	FORA(5L, "FORA"),
	EMMOVIMENTACAO(6L, "EM MOVIMENTAÇÃO"),
	PARAREVISAO(7L, "PARA REVISÃO"),
	AGUARDANDOLIBERACAO(8L, "AGUARDANDO LIBERAÇÃO"),
	AGUARDANDOFINALIZACAO(9L, "AGUARDANDO FINALIZAÇÃO");
	
	private Long id;
	private String descricao;
	
	
	
	private StatusViaturaEnum(Long id, String descricao) {
		this.id = id;
		this.descricao = descricao;
	}


	public Long getId() {
		return id;
	}


	public String getDescricao() {
		return descricao;
	}
	
	
}
