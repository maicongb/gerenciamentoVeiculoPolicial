package br.gov.df.pm.domain.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity(name = "TIPOVEICULO")
public class TipoVeiculo {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	@Column(name = "tve_Codigo")
	private Long id;
	
	@Column(name = "tve_Nome", nullable = false)
	private String nome;
	
	@Column(name = "tve_Ativo", nullable = false)
	private Integer ativo;

	@OneToMany(mappedBy = "tipoVeiculo")
	private List<MarcaVeiculo> marcaVeiculos = new ArrayList<>();
	
	@PrePersist
	public void setarAtivo() {
		setAtivo(1);
	}
	
	
}
