package br.gov.df.pm.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.gov.df.pm.domain.exception.StatusViaturaNaoEncontradaException;
import br.gov.df.pm.domain.model.StatusViatura;
import br.gov.df.pm.domain.repository.StatusViaturaRepository;

@Service
public class CadastroStatusViaturaService {
	
	@Autowired
	private StatusViaturaRepository statusViaturaRepository;
	
	public StatusViatura buscarOuFalhar(Long statusViaturaId) {
		return statusViaturaRepository.findById(statusViaturaId)
				.orElseThrow(() -> new StatusViaturaNaoEncontradaException(statusViaturaId));
	}


}
