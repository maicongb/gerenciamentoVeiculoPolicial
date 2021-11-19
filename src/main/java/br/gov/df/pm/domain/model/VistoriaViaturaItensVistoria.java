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

//MAPEAMENTO ESTÁ INCOMPLETO, DEVERÁ SER FEITO
//QUANDO IMPLEMENTAR VISTORIA DA VIATURA

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity(name = "VISTORIAVIATURA_ITENSVISTORIA")
public class VistoriaViaturaItensVistoria {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	@Column(name = "viv_Codigo")
	private Long id;
	
	@JoinColumn(name="itv_Codigo", nullable=false)
	@ManyToOne
	private ItensVistoria itensVistoria;

	
	@Column(name="viv_VistoriaOk",unique=true, nullable=false)
	private int vistoriaOk;
	
}
