package br.gov.df.pm.core.modelmapper;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.gov.df.pm.api.model.ViaturaModel;
import br.gov.df.pm.domain.model.Viatura;

@Configuration
public class ModelMapperConfig {
	
	@Bean
	public ModelMapper modelMapper() {
		var modelMapper = new ModelMapper();
		
		
		//SETAR A MARCA DENTRO DE ModeloVeiculoResumoModel
		var viaturaToViaturaModelTypeMap = modelMapper.createTypeMap(
				Viatura.class , ViaturaModel.class);
		
		viaturaToViaturaModelTypeMap.<String>addMapping(
				viaturaSrc -> viaturaSrc.getUfPlaca().getSigla(), 
				(viaturaModeldest, value) -> viaturaModeldest.setUfPlaca(value));
		
		viaturaToViaturaModelTypeMap.<String>addMapping(
				viaturaSrc -> viaturaSrc.getStatusViatura().getNome(), 
				(viaturaModeldest, value) -> viaturaModeldest.setStatusViatura(value));
		
		viaturaToViaturaModelTypeMap.<String>addMapping(
				viaturaSrc -> viaturaSrc.getMotivoExclusao().getNome(), 
				(viaturaModeldest, value) -> viaturaModeldest.setMotivoExclusao(value));
		
		return modelMapper;
	}

}
