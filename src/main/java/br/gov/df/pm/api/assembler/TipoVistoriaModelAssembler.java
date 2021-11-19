package br.gov.df.pm.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.gov.df.pm.api.model.TipoVistoriaModel;
import br.gov.df.pm.domain.model.TipoVistoria;

@Component
public class TipoVistoriaModelAssembler {

	@Autowired
	private ModelMapper modelMapper;
	
	public TipoVistoriaModel toModel(TipoVistoria tipoVistoria) {
		
		return modelMapper.map(tipoVistoria, TipoVistoriaModel.class);
	}
	
	public List<TipoVistoriaModel> toCollectionModel(List<TipoVistoria> tipoVistorias) {
		return tipoVistorias.stream()
				.map(tipoVistoria -> toModel(tipoVistoria))
				.collect(Collectors.toList());
	}
	
}
