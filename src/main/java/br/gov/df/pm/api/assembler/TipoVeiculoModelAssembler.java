package br.gov.df.pm.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.gov.df.pm.api.model.TipoVeiculoModel;
import br.gov.df.pm.domain.model.TipoVeiculo;

@Component
public class TipoVeiculoModelAssembler {

	@Autowired
	private ModelMapper modelMapper;
	
	public TipoVeiculoModel toModel(TipoVeiculo tipoVeiculo) {
		
		return modelMapper.map(tipoVeiculo, TipoVeiculoModel.class);
	}
	
	public List<TipoVeiculoModel> toCollectionModel(List<TipoVeiculo> tipoVeiculos) {
		return tipoVeiculos.stream()
				.map(tipoVeiculo -> toModel(tipoVeiculo))
				.collect(Collectors.toList());
	}
	
}
