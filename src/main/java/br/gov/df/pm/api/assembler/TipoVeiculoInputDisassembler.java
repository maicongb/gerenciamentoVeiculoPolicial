package br.gov.df.pm.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.gov.df.pm.api.model.input.TipoVeiculoInput;
import br.gov.df.pm.domain.model.TipoVeiculo;

@Component
public class TipoVeiculoInputDisassembler {

	@Autowired
	private ModelMapper modelMapper;
	
	public TipoVeiculo toDomainObject(
			TipoVeiculoInput tipoVeiculoInput) {
		return modelMapper.map(tipoVeiculoInput, TipoVeiculo.class);
	}
	
	public void copyToDomainObject(TipoVeiculoInput tipoVeiculoInput, 
			TipoVeiculo tipoVeiculo) {
		
		modelMapper.map(tipoVeiculoInput, tipoVeiculo);
	}
}
