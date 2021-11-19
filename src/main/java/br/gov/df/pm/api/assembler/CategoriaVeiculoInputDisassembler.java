package br.gov.df.pm.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.gov.df.pm.api.model.input.CategoriaVeiculoInput;
import br.gov.df.pm.domain.model.CategoriaVeiculo;

@Component
public class CategoriaVeiculoInputDisassembler {

	@Autowired
	private ModelMapper modelMapper;
	
	public CategoriaVeiculo toDomainObject(
			CategoriaVeiculoInput categoriaVeiculoInput) {
		return modelMapper.map(categoriaVeiculoInput, CategoriaVeiculo.class);
	}
	
	public void copyToDomainObject(CategoriaVeiculoInput categoriaVeiculoInput, 
			CategoriaVeiculo categoriaVeiculo) {
		
		modelMapper.map(categoriaVeiculoInput, categoriaVeiculo);
	}
}
