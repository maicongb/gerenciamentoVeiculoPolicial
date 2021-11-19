package br.gov.df.pm.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.gov.df.pm.domain.exception.UnidadePolicialMilitarNaoEncontradaException;
import br.gov.df.pm.domain.model.UnidadePolicialMilitar;
import br.gov.df.pm.domain.repository.UnidadePolicialMilitarRepository;

@Service
public class CadastroUnidadePolicialMilitarService {
	
	@Autowired
	private UnidadePolicialMilitarRepository unidadePolicialMilitarRepository;
	
	public UnidadePolicialMilitar buscarOuFalhar(Long ufId) {
		return unidadePolicialMilitarRepository.findById(ufId)
				.orElseThrow(() -> new UnidadePolicialMilitarNaoEncontradaException(ufId));
	}

}
