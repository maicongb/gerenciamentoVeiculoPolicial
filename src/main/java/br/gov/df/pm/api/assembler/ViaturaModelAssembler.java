package br.gov.df.pm.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.gov.df.pm.api.model.ViaturaModel;
import br.gov.df.pm.domain.model.Viatura;

@Component
public class ViaturaModelAssembler {

	@Autowired
	private ModelMapper modelMapper;
	
	public ViaturaModel toModel(Viatura viatura) {
		
		return modelMapper.map(viatura, ViaturaModel.class);
	}
	
	public List<ViaturaModel> toCollectionModel(List<Viatura> viaturas) {
		return viaturas.stream()
				.map(viatura -> toModel(viatura))
				.collect(Collectors.toList());
	}
	
}
