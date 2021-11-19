package br.gov.df.pm.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.gov.df.pm.api.model.MarcaVeiculoModel;
import br.gov.df.pm.domain.model.MarcaVeiculo;

@Component
public class MarcaVeiculoModelAssembler {

	@Autowired
	private ModelMapper modelMapper;
	
	public MarcaVeiculoModel toModel(MarcaVeiculo marcaVeiculo) {
		
		return modelMapper.map(marcaVeiculo, MarcaVeiculoModel.class);
	}
	
	public List<MarcaVeiculoModel> toCollectionModel(List<MarcaVeiculo> marcaVeiculos) {
		return marcaVeiculos.stream()
				.map(marcaVeiculo -> toModel(marcaVeiculo))
				.collect(Collectors.toList());
	}
	
}
