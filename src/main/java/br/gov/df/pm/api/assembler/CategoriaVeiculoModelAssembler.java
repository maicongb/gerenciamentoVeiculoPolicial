package br.gov.df.pm.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.gov.df.pm.api.model.CategoriaVeiculoModel;
import br.gov.df.pm.domain.model.CategoriaVeiculo;

@Component
public class CategoriaVeiculoModelAssembler {

	@Autowired
	private ModelMapper modelMapper;
	
	public CategoriaVeiculoModel toModel(CategoriaVeiculo categoriaVeiculo) {
		
		return modelMapper.map(categoriaVeiculo, CategoriaVeiculoModel.class);
	}
	
	public List<CategoriaVeiculoModel> toCollectionModel(List<CategoriaVeiculo> categoriaVeiculos) {
		return categoriaVeiculos.stream()
				.map(categoriaVeiculo -> toModel(categoriaVeiculo))
				.collect(Collectors.toList());
	}
	
}
