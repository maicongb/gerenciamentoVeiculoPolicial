package br.gov.df.pm.api.model;

import java.time.LocalDate;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ViaturaModel {
	
	private String id;
	private String prefixo;
	private LocalDate anoFabricacao; 
	private LocalDate anoModelo;
	private String placa;
    private String ufPlaca;
	private PlacaVinculadaResumoModal placaVinculada; 
	private String chassi;
	private String renavam; 
	private String numeroMotor;
    private TipoCombustivelModel tipoCombustivel;
	private String cor; 
	private Integer odometro; 
    private ModeloVeiculoModel modeloVeiculo;
	private LocalDate dtProximaRevisao; 
	private Integer odometroProximaRevisao; 
	private TipoViaturaModel tipoViatura;
    private String statusViatura;
	private String motivoExclusao;
	private LocalDate dtAquisicao; 
	private LocalDate dtInclusao; 
	private LocalDate dtExclusao; 
	private String numeroSei;
	private String tombamento; 
	private String observacaoExclusao;
	private List<ViaturaUpmResumoModal> viaturasUpm;
	private Integer ativo;
	
}
