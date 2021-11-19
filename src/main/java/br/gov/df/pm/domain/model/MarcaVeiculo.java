package br.gov.df.pm.domain.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity(name = "MARCAVEICULO")
public class MarcaVeiculo {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	@Column(name = "mav_Codigo")
	private Long id;
	
	@Column(name = "mav_Nome", nullable = false)
	private String nome;
	
	@Column(name = "mav_Abreviacao", nullable = false)
	private String abreviacao;
	
	@Column(name = "mav_Ativo", nullable = false)
	private Integer ativo = 1;
	
	@ManyToOne
	@JoinColumn(name = "tve_Codigo", nullable = false)
	private TipoVeiculo tipoVeiculo;

}
