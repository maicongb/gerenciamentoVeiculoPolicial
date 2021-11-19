package br.gov.df.pm.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.gov.df.pm.api.model.MotivoBaixaViaturaModel;
import br.gov.df.pm.domain.model.MotivoBaixaViatura;

@Component
public class MotivoBaixaViaturaModelAssembler {

	@Autowired
	private ModelMapper modelMapper;
	
	public MotivoBaixaViaturaModel toModel(MotivoBaixaViatura motivoBaixaViatura) {
		
		return modelMapper.map(motivoBaixaViatura, MotivoBaixaViaturaModel.class);
	}
	
	public List<MotivoBaixaViaturaModel> toCollectionModel(List<MotivoBaixaViatura> motivoBaixaViaturas) {
		return motivoBaixaViaturas.stream()
				.map(motivoBaixaViatura -> toModel(motivoBaixaViatura))
				.collect(Collectors.toList());
	}
	
}
