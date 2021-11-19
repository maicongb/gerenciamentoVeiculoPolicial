package br.gov.df.pm.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.gov.df.pm.api.model.TipoViaturaModel;
import br.gov.df.pm.domain.model.TipoViatura;

@Component
public class TipoViaturaModelAssembler {

	@Autowired
	private ModelMapper modelMapper;
	
	public TipoViaturaModel toModel(TipoViatura tipoViatura) {
		
		return modelMapper.map(tipoViatura, TipoViaturaModel.class);
	}
	
	public List<TipoViaturaModel> toCollectionModel(List<TipoViatura> tipoViaturas) {
		return tipoViaturas.stream()
				.map(tipoViatura -> toModel(tipoViatura))
				.collect(Collectors.toList());
	}
	
}
