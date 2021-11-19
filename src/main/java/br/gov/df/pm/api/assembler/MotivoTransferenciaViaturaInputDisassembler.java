package br.gov.df.pm.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.gov.df.pm.api.model.input.MotivoTransferenciaViaturaInput;
import br.gov.df.pm.domain.model.MotivoTransferenciaViatura;

@Component
public class MotivoTransferenciaViaturaInputDisassembler {

	@Autowired
	private ModelMapper modelMapper;
	
	public MotivoTransferenciaViatura toDomainObject(
			MotivoTransferenciaViaturaInput motivoTransferenciaViaturaInput) {
		return modelMapper.map(motivoTransferenciaViaturaInput, MotivoTransferenciaViatura.class);
	}
	
	public void copyToDomainObject(MotivoTransferenciaViaturaInput motivoTransferenciaViaturaInput, 
			MotivoTransferenciaViatura motivoTransferenciaViatura) {
		
		modelMapper.map(motivoTransferenciaViaturaInput, motivoTransferenciaViatura);
	}
}
