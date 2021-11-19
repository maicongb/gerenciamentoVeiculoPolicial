package br.gov.df.pm.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.gov.df.pm.api.model.input.TipoVistoriaInput;
import br.gov.df.pm.domain.model.TipoVistoria;

@Component
public class TipoVistoriaInputDisassembler {

	@Autowired
	private ModelMapper modelMapper;
	
	public TipoVistoria toDomainObject(TipoVistoriaInput tipoVistoriaInput) {
		return modelMapper.map(tipoVistoriaInput, TipoVistoria.class);
	}
	
	public void copyToDomainObject(TipoVistoriaInput tipoVistoriaInput, 
					TipoVistoria tipoVistoria) {
		
		modelMapper.map(tipoVistoriaInput, tipoVistoria);
	}
	
}
