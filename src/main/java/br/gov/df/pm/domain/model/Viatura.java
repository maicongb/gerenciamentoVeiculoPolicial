package br.gov.df.pm.domain.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity(name = "VIATURA")
public class Viatura {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	@Column(name = "vtr_Codigo")
	private Long codigo;
	
	@Column(name = "vtr_Id", nullable = false)
	private String id;
	
	@Column(name = "vtr_Prefixo", nullable = false)
	private String prefixo;
	
	@Column(name = "vtr_AnoFabricacao", nullable = false, columnDefinition = "datetime")
	private LocalDate anoFabricacao; 
	
	@Column(name = "vtr_AnoModelo", nullable = false, columnDefinition = "datetime")
	private LocalDate anoModelo;
	
	@Column(name = "vtr_placa", nullable = false)
	private String placa;
	
	@Column(name = "vtr_Chassi", nullable = false)
	private String chassi;
	
	@Column(name = "vtr_Tombamento", nullable = false)
	private String tombamento; 
	
	@Column(name = "vtr_Renavam", nullable = false)
	private String renavam;
	
	@Column(name = "vtr_DtAquisicao", nullable = false, columnDefinition = "datetime")
	private LocalDate dtAquisicao; 
	
	@Column(name = "vtr_DtInclusao", nullable = false, columnDefinition = "datetime")
	private LocalDate dtInclusao; 
	
	@Column(name = "vtr_DtExclusao", columnDefinition = "datetime")
	private LocalDate dtExclusao; 
	
	@Column(name = "vtr_NrMotor")
	private String numeroMotor;
	
	@Column(name = "vtr_Cor", nullable = true)
	private String cor; 
	
	@Column(name = "vtr_NrSei", nullable = true)
	private String numeroSei; 
	
	@Column(name = "vtr_ObservacaoExclusao", nullable = true)
	private String observacaoExclusao; 
	
	@Column(name = "vtr_Odometro", nullable = false)
	private Integer odometro; 
	
	@Column(name = "vtr_DtProximaRevisao", nullable = false, columnDefinition = "datetime")
	private LocalDate dtProximaRevisao; 
	
	@Column(name = "vtr_OdometroProximaRevisao", nullable = false)
	private Integer odometroProximaRevisao; 
	
	@Column(name = "vtr_Ativo", nullable = false)
	private Integer ativo = 1;
	
	@ManyToOne
	@JoinColumn(name = "mov_Codigo", nullable = false)
    private ModeloVeiculo modeloVeiculo;
	
	@ManyToOne
	@JoinColumn(name = "ufe_CodigoPlaca", referencedColumnName = "ufe_Codigo")
    private UnidadeFederativa ufPlaca;
	
    @ManyToOne
    @JoinColumn(name = "tpo_Codigo", nullable = false)
    private TipoCombustivel tipoCombustivel;
    
    @ManyToOne
    @JoinColumn(name = "tpv_Codigo", nullable = false)
    private TipoViatura tipoViatura;

    @ManyToOne
    @JoinColumn(name = "svi_Codigo", nullable = false)
    private StatusViatura statusViatura;
    
    @ManyToOne
	@JoinColumn(name="mev_Codigo",  nullable = true)
	private MotivoExclusaoViatura motivoExclusao;
    
	@OneToOne
	@JoinColumn(name = "plv_Codigo")
	private PlacaVinculada placaVinculada; 
	
	@OneToMany(mappedBy = "viatura", fetch = FetchType.LAZY)
	private List<ViaturaUpm> viaturasUpm = new ArrayList<>();
	
	public boolean isNova() {
		return codigo == null;
	}
	
	public void ativar() {
		setAtivo(1);
	}
	
	public void inativar() {
		setAtivo(0);
	}
	
	@PrePersist
	public void gerarId() {
		setId(UUID.randomUUID().toString());
	}
}
