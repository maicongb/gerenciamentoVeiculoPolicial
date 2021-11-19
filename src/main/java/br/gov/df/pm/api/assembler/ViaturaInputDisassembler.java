package br.gov.df.pm.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.gov.df.pm.api.model.input.ViaturaInput;
import br.gov.df.pm.domain.model.ModeloVeiculo;
import br.gov.df.pm.domain.model.TipoCombustivel;
import br.gov.df.pm.domain.model.TipoViatura;
import br.gov.df.pm.domain.model.UnidadeFederativa;
import br.gov.df.pm.domain.model.Viatura;

@Component
public class ViaturaInputDisassembler {

	@Autowired
	private ModelMapper modelMapper;
	
	public Viatura toDomainObject(
			ViaturaInput viaturaInput) {
		return modelMapper.map(viaturaInput, Viatura.class);
	}
	
	public void copyToDomainObject(ViaturaInput viaturaInput, 
			Viatura viatura) {
		
		// Para evitar org.hibernate.HibernateException: identifier of an instance of 
		// br.gov.df.pm.domain.model.Viatura was altered from 4 to 1
		viatura.setUfPlaca(new UnidadeFederativa());
		
		// Para evitar org.hibernate.HibernateException: identifier of an instance of
		//br.gov.df.pm.domain.model.ModeloVeiculo was altered from 1 to 3
		viatura.setModeloVeiculo(new ModeloVeiculo());
		
		// Para evitar org.hibernate.HibernateException: identifier of an instance of
		//br.gov.df.pm.domain.model.TipoCombustivel was altered from 1 to 2
		viatura.setTipoCombustivel(new TipoCombustivel());
		
		// Para evitar org.hibernate.HibernateException: identifier of an instance of
		//br.gov.df.pm.domain.model.TipoViatura was altered from 1 to 2
		viatura.setTipoViatura(new TipoViatura());
	
		modelMapper.map(viaturaInput, viatura);
	}
}
