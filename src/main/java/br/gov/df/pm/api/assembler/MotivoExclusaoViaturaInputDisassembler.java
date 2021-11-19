package br.gov.df.pm.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.gov.df.pm.api.model.input.MotivoExclusaoViaturaInput;
import br.gov.df.pm.domain.model.MotivoExclusaoViatura;

@Component
public class MotivoExclusaoViaturaInputDisassembler {

	@Autowired
	private ModelMapper modelMapper;
	
	public MotivoExclusaoViatura toDomainObject(
				MotivoExclusaoViaturaInput motivoExclusaoViaturaInput) {
		return modelMapper.map(motivoExclusaoViaturaInput, MotivoExclusaoViatura.class);
	}
	
	public void copyToDomainObject(MotivoExclusaoViaturaInput motivoExclusaoViaturaInput, 
			MotivoExclusaoViatura motivoExclusaoViatura) {
		
		modelMapper.map(motivoExclusaoViaturaInput, motivoExclusaoViatura);
	}
}
