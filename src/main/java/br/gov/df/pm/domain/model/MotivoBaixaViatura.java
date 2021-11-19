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
@Entity(name = "MOTIVOBAIXAVIATURA")
public class MotivoBaixaViatura {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	@Column(name = "mbv_Codigo")
	private Long id;
	
	@Column(name = "mbv_Nome", nullable = false)
	private String nome;
	
	@Column(name = "mbv_Ativo", nullable = false)
	private Integer ativo;
	
	@PrePersist
	public void setarAtivo() {
		setAtivo(1);
	}

}
