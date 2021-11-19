package br.gov.df.pm.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.gov.df.pm.api.model.input.ItensVistoriaInput;
import br.gov.df.pm.domain.model.ItensVistoria;
import br.gov.df.pm.domain.model.TipoVistoria;

@Component
public class ItensVistoriaInputDisassembler {

	@Autowired
	private ModelMapper modelMapper;
	
	public ItensVistoria toDomainObject(
			ItensVistoriaInput itensVistoriaInput) {
		return modelMapper.map(itensVistoriaInput, ItensVistoria.class);
	}
	
	public void copyToDomainObject(ItensVistoriaInput itensVistoriaInput, 
			ItensVistoria itensVistoria) {
		
		// Para evitar org.hibernate.HibernateException: identifier of an instance of 
		// br.gov.df.pm.domain.model.TipoVistoria was altered from 1 to 2
		itensVistoria.setTipoVistoria(new TipoVistoria());
		
		modelMapper.map(itensVistoriaInput, itensVistoria);
	}
}
