package br.gov.df.pm.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.gov.df.pm.api.model.input.MarcaVeiculoInput;
import br.gov.df.pm.domain.model.MarcaVeiculo;
import br.gov.df.pm.domain.model.TipoVeiculo;

@Component
public class MarcaVeiculoInputDisassembler {

	@Autowired
	private ModelMapper modelMapper;
	
	public MarcaVeiculo toDomainObject(
			MarcaVeiculoInput marcaVeiculoInput) {
		return modelMapper.map(marcaVeiculoInput, MarcaVeiculo.class);
	}
	
	public void copyToDomainObject(MarcaVeiculoInput marcaVeiculoInput, 
			MarcaVeiculo marcaVeiculo) {
		
		// Para evitar org.hibernate.HibernateException: identifier of an instance of 
		// br.gov.df.pm.domain.model.TipoVeiculo was altered from 2 to 9
		marcaVeiculo.setTipoVeiculo(new TipoVeiculo());
		
		modelMapper.map(marcaVeiculoInput, marcaVeiculo);
	}
}
