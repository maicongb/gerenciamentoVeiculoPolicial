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

import lombok.Data;
import lombok.EqualsAndHashCode;

//TABELA NÃO COMPLETA, QUANDO FIZER O BAIXAR VIATURA
//DEVE COMPLETAR A TABELA

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity(name = "HISTBAIXAVIATURA")
public class HistoricoBaixaViatura {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	@Column(name = "hbv_Codigo")
	private Long id; 
	
	@Column(name = "hbv_DtEntrada", nullable = false, columnDefinition = "datetime")
	private LocalDate dtEntrada; 
	
	@Column(name = "hbv_DtSaida", nullable = true, columnDefinition = "datetime")
	private LocalDate dtSaida; 
	
	@Column(name = "hbv_OdometroBaixa", nullable = false)
	private Integer odometroBaixa; 
	
	@Column(name = "hbv_OdometroLiberacao", nullable = false)
	private Integer odometroLiberacao; 

	@Column(name = "hbv_DescricaoServicos")
	private String descricaoServico;
	
	@Column(name = "hbv_LocalBaixa")
	private String localBaixa;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="mbv_Codigo")
	private MotivoBaixaViatura motivoBaixaViatura;
	

}
