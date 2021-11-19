package br.gov.df.pm.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.gov.df.pm.domain.exception.UnidadeFederativaNaoEncontradaException;
import br.gov.df.pm.domain.model.UnidadeFederativa;
import br.gov.df.pm.domain.repository.UnidadeFederativaRepository;

@Service
public class CadastroUnidadeFederativaService {
	
	@Autowired
	private UnidadeFederativaRepository unidadeFederativaRepository;
	
	public UnidadeFederativa buscarOuFalhar(Long ufId) {
		return unidadeFederativaRepository.findById(ufId)
				.orElseThrow(() -> new UnidadeFederativaNaoEncontradaException(ufId));
	}

}
