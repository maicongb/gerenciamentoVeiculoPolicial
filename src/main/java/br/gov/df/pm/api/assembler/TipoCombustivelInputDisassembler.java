package br.gov.df.pm.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.gov.df.pm.api.model.input.TipoCombustivelInput;
import br.gov.df.pm.domain.model.TipoCombustivel;

@Component
public class TipoCombustivelInputDisassembler {

	@Autowired
	private ModelMapper modelMapper;
	
	public TipoCombustivel toDomainObject(TipoCombustivelInput tipoCombustivelInput) {
		return modelMapper.map(tipoCombustivelInput, TipoCombustivel.class);
	}
	
	public void copyToDomainObject(TipoCombustivelInput tipoCombustivelInput, 
					TipoCombustivel tipoCombustivel) {
		
		modelMapper.map(tipoCombustivelInput, tipoCombustivel);
	}
	
}
