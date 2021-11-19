package br.gov.df.pm.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.gov.df.pm.api.model.ItensVistoriaModel;
import br.gov.df.pm.domain.model.ItensVistoria;

@Component
public class ItensVistoriaModelAssembler {

	@Autowired
	private ModelMapper modelMapper;
	
	public ItensVistoriaModel toModel(ItensVistoria itensVistoria) {
		
		return modelMapper.map(itensVistoria, ItensVistoriaModel.class);
	}
	
	public List<ItensVistoriaModel> toCollectionModel(List<ItensVistoria> itensVistorias) {
		return itensVistorias.stream()
				.map(itensVistoria -> toModel(itensVistoria))
				.collect(Collectors.toList());
	}
	
}
