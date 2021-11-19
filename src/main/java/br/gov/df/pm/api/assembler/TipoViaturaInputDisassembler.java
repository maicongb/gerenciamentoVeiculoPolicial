package br.gov.df.pm.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.gov.df.pm.api.model.input.TipoViaturaInput;
import br.gov.df.pm.domain.model.TipoViatura;

@Component
public class TipoViaturaInputDisassembler {

	@Autowired
	private ModelMapper modelMapper;
	
	public TipoViatura toDomainObject(
			TipoViaturaInput tipoViaturaInput) {
		return modelMapper.map(tipoViaturaInput, TipoViatura.class);
	}
	
	public void copyToDomainObject(TipoViaturaInput tipoViaturaInput, 
			TipoViatura tipoViatura) {
		
		modelMapper.map(tipoViaturaInput, tipoViatura);
	}
}
