package br.gov.df.pm.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.gov.df.pm.api.model.MotivoTransferenciaViaturaModel;
import br.gov.df.pm.domain.model.MotivoTransferenciaViatura;

@Component
public class MotivoTransferenciaViaturaModelAssembler {

	@Autowired
	private ModelMapper modelMapper;
	
	public MotivoTransferenciaViaturaModel toModel(MotivoTransferenciaViatura 
				motivoTransferenciaViatura) {
		
		return modelMapper.map(motivoTransferenciaViatura, MotivoTransferenciaViaturaModel.class);
	}
	
	public List<MotivoTransferenciaViaturaModel> toCollectionModel(List<MotivoTransferenciaViatura> motivoTransferenciaViaturas) {
		return motivoTransferenciaViaturas.stream()
				.map(motivoTransferenciaViatura -> toModel(motivoTransferenciaViatura))
				.collect(Collectors.toList());
	}
	
}
