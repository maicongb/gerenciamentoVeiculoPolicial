package br.gov.df.pm.api.model.input;

import java.time.LocalDate;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import br.gov.df.pm.core.validation.AnoModelo;
import br.gov.df.pm.core.validation.Placa;
import lombok.Getter;
import lombok.Setter;

@AnoModelo(anoModeloField = "anoModelo", anoFabricacaoField = "anoFabricacao")
@Placa(placaField = "placa")
@Getter
@Setter
public class ViaturaInput {
	
	@NotBlank
	private String prefixo;
	
	@NotBlank
	private String placa;
	
	@Valid
	@NotNull
	private UfPlacaIdInput ufPlaca;
	
	@NotBlank
	private String renavam;
	
	@NotBlank
	private String chassi;
	
	@NotBlank
	private String numeroMotor;
	
	@NotNull
	private LocalDate anoFabricacao;
	
	@NotNull
	private LocalDate anoModelo;
	
	@NotBlank
	private String cor;
	
	@NotNull
	private Integer odometro; 
	
	@NotNull
	private LocalDate dtProximaRevisao;
	
	@NotNull
	private Integer odometroProximaRevisao; 
	
	@NotNull
	private LocalDate dtAquisicao; 
	
	@NotNull
	private LocalDate dtInclusao; 
	
	@NotBlank
	private String tombamento; 
	
	@NotBlank
	private String numeroSei; 
	
	@Valid
	@NotNull
	private ModeloVeiculoIdInput modeloVeiculo;
	
	@Valid
	@NotNull
    private TipoCombustivelIdInput tipoCombustivel;
	
	@Valid
	@NotNull
    private TipoViaturaIdInput tipoViatura;
	
	@Valid
	@Size(min = 1)
	@NotNull
	private List<ViaturaUpmResumoInput> viaturasUpm;
	

}
