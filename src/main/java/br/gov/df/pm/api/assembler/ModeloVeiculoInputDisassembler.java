package br.gov.df.pm.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.gov.df.pm.api.model.input.ModeloVeiculoInput;
import br.gov.df.pm.domain.model.CategoriaVeiculo;
import br.gov.df.pm.domain.model.MarcaVeiculo;
import br.gov.df.pm.domain.model.ModeloVeiculo;

@Component
public class ModeloVeiculoInputDisassembler {

	@Autowired
	private ModelMapper modelMapper;
	
	public ModeloVeiculo toDomainObject(
			ModeloVeiculoInput modeloVeiculoInput) {
		return modelMapper.map(modeloVeiculoInput, ModeloVeiculo.class);
	}
	
	public void copyToDomainObject(ModeloVeiculoInput modeloVeiculoInput, 
			ModeloVeiculo modeloVeiculo) {
		
		// Para evitar org.hibernate.HibernateException: identifier of an instance of 
		// br.gov.df.pm.domain.model.MarcaVeiculo was altered from 4 to 1
		modeloVeiculo.setMarcaVeiculo(new MarcaVeiculo());
		
		// Para evitar org.hibernate.HibernateException: identifier of an instance of
		//br.gov.df.pm.domain.model.CategoriaVeiculo was altered from 1 to 3
		modeloVeiculo.setCategoriaVeiculo(new CategoriaVeiculo());
		
	
		modelMapper.map(modeloVeiculoInput, modeloVeiculo);
	}
}
