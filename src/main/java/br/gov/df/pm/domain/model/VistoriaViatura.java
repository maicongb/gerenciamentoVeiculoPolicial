package br.gov.df.pm.domain.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity(name = "VISTORIAVIATURA")
public class VistoriaViatura {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	@Column(name = "vvi_Codigo")
	private Long id;
	
	@Column(name = "vvi_odometro", nullable = false)
	private Integer odometro; 
	
	@Column(name = "vvi_DtVistoria", nullable = false, columnDefinition = "datetime")
	private LocalDate dtVistoria; 
	
	@Column(name = "vvi_DtLiberacao", columnDefinition = "datetime")
	private LocalDate dtLiberacao; 
	
	@JoinColumn(name = "tiv_Codigo", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private TipoVistoria tipoVistoria; 

	@Column(name = "vvi_Ativo", nullable = false)
	private Integer ativo;
	
	@PrePersist
	public void setarAtivo() {
		setAtivo(1);
	}

}
