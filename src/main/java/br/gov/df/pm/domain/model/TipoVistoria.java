package br.gov.df.pm.domain.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity(name = "TIPOVISTORIA")
public class TipoVistoria {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	@Column(name = "tiv_Codigo")
	private Long id;
	
	@Column(name = "tiv_Nome", nullable = false)
	private String nome;
	
	@Column(name = "tiv_Ativo", nullable = false)
	private Integer ativo;
	
	@PrePersist
	public void setarAtivo() {
		setAtivo(1);
	}

}
