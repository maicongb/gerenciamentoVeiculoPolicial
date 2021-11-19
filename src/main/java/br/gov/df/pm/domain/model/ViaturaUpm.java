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
@Entity(name = "VIATURA_UPM")
public class ViaturaUpm {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	@Column(name="viu_Codigo")
	private Long id; 
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "vtr_Codigo", nullable = false)
	private Viatura viatura;
	
	@ManyToOne
	@JoinColumn(name = "upm_Codigo", nullable = false)
	private UnidadePolicialMilitar upm;
	
	@Column(name="viu_DtEntrada", nullable = false)
	private LocalDate dataEntrada;
	
	@Column(name="viu_DtSaida")
	private LocalDate dataSaida;
	
	@Column(name="viu_DtMovimentacao", nullable = false)
	private LocalDate dataMovimentacao;
	
	@Column(name="viu_NrSei")
	private String numeroSei;
	
	@Column(name="viu_Observacao")
	private String observacao;
	
	@Column(name = "viu_Ativo", nullable = false)
	private Integer ativo = 1;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "mtv_Codigo", nullable = true)
	private MotivoTransferenciaViatura motivoTransferenciaViatura;
	
	public boolean isNova() {
		return id == null;
	}
	
	@PrePersist
	protected void onCreate() {
		this.dataEntrada = LocalDate.now();
		this.dataMovimentacao = LocalDate.now();
	}
	
}
