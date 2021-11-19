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
@Entity(name = "MODELOVEICULO")
public class ModeloVeiculo {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	@Column(name = "mov_Codigo")
	private Long id;
	
	@Column(name = "mov_Nome", nullable = false)
	private String nome;
	
	@Column(name = "mov_Potencia", nullable = false)
	private String potencia;
	
	@Column(name = "mov_Tanque", nullable = false)
	private Integer tanque;
	
	@Column(name = "mov_Importado", nullable = false)
	private Integer importado;
	
	@Column(name = "mov_Ativo", nullable = false)
	private Integer ativo = 1;
	
	@ManyToOne
	@JoinColumn(name = "mav_Codigo", nullable = false)
	private MarcaVeiculo marcaVeiculo;
	
	@ManyToOne
	@JoinColumn(name = "cav_Codigo", nullable = false)
	private CategoriaVeiculo categoriaVeiculo;

}
