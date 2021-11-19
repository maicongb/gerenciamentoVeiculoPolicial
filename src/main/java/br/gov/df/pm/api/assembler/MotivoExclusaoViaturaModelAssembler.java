package br.gov.df.pm.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.gov.df.pm.api.model.MotivoExclusaoViaturaModel;
import br.gov.df.pm.domain.model.MotivoExclusaoViatura;

@Component
public class MotivoExclusaoViaturaModelAssembler {

	@Autowired
	private ModelMapper modelMapper;
	
	
	public MotivoExclusaoViaturaModel toModel(MotivoExclusaoViatura motivoExclusaoViatura) {
		
		return modelMapper.map(motivoExclusaoViatura, MotivoExclusaoViaturaModel.class);
	}
	
	public List<MotivoExclusaoViaturaModel> toCollectionModel(List<MotivoExclusaoViatura> motivoExclusaoViaturas) {
		return motivoExclusaoViaturas.stream()
				.map(motivoExclusaoViatura -> toModel(motivoExclusaoViatura))
				.collect(Collectors.toList());
	}
	
}
