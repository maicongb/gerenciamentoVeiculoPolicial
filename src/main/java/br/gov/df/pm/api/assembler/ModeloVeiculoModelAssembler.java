package br.gov.df.pm.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.gov.df.pm.api.model.ModeloVeiculoModel;
import br.gov.df.pm.domain.model.ModeloVeiculo;

@Component
public class ModeloVeiculoModelAssembler {

	@Autowired
	private ModelMapper modelMapper;
	
	public ModeloVeiculoModel toModel(ModeloVeiculo modeloVeiculo) {
		
		return modelMapper.map(modeloVeiculo, ModeloVeiculoModel.class);
	}
	
	public List<ModeloVeiculoModel> toCollectionModel(List<ModeloVeiculo> modeloVeiculos) {
		return modeloVeiculos.stream()
				.map(modeloVeiculo -> toModel(modeloVeiculo))
				.collect(Collectors.toList());
	}
	
}
