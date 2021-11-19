package br.gov.df.pm.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.gov.df.pm.api.model.input.MotivoBaixaViaturaInput;
import br.gov.df.pm.domain.model.MotivoBaixaViatura;

@Component
public class MotivoBaixaViaturaInputDisassembler {

	@Autowired
	private ModelMapper modelMapper;
	
	public MotivoBaixaViatura toDomainObject(
			MotivoBaixaViaturaInput motivoBaixaViaturaInput) {
		return modelMapper.map(motivoBaixaViaturaInput, MotivoBaixaViatura.class);
	}
	
	public void copyToDomainObject(MotivoBaixaViaturaInput motivoBaixaViaturaInput, 
			MotivoBaixaViatura motivoBaixaViatura) {
		
		modelMapper.map(motivoBaixaViaturaInput, motivoBaixaViatura);
	}
}
