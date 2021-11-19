package br.gov.df.pm.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.gov.df.pm.api.model.input.ViaturaResumoInput;
import br.gov.df.pm.domain.model.ModeloVeiculo;
import br.gov.df.pm.domain.model.TipoCombustivel;
import br.gov.df.pm.domain.model.TipoViatura;
import br.gov.df.pm.domain.model.UnidadeFederativa;
import br.gov.df.pm.domain.model.Viatura;

@Component
public class ViaturaResumoInputDisassembler {

	@Autowired
	private ModelMapper modelMapper;
	
	public Viatura toDomainObject(
			ViaturaResumoInput viaturaResumoInput) {
		return modelMapper.map(viaturaResumoInput, Viatura.class);
	}
	
	public void copyToDomainObject(ViaturaResumoInput viaturaResumoInput, 
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
		
		// Para evitar org.hibernate.HibernateException: identifier of an instance of
		//br.gov.df.pm.domain.model.TipoViatura was altered from 1 to 2
//		viatura.getViaturasUpm().forEach(viaturaUpm -> {
//			viaturaUpm.setUpm(new UnidadePolicialMilitar());
//		});
		
	
		modelMapper.map(viaturaResumoInput, viatura);
	}
}
