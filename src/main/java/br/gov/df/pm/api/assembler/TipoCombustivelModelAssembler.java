package br.gov.df.pm.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.gov.df.pm.api.model.TipoCombustivelModel;
import br.gov.df.pm.domain.model.TipoCombustivel;

@Component
public class TipoCombustivelModelAssembler {

	@Autowired
	private ModelMapper modelMapper;
	
	public TipoCombustivelModel toModel(TipoCombustivel tipoCombustivel) {
		return modelMapper.map(tipoCombustivel, TipoCombustivelModel.class);
	}
	
	public List<TipoCombustivelModel> toCollectionModel(List<TipoCombustivel> tipoCombustiveis) {
		return tipoCombustiveis.stream()		
					.map(tipoCombustivel -> toModel(tipoCombustivel))
					.collect(Collectors.toList());
	}
}
