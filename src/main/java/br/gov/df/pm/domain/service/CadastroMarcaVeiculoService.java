package br.gov.df.pm.domain.service;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import br.gov.df.pm.domain.enumeration.StatusMarcaVeiculo;
import br.gov.df.pm.domain.exception.EntidadeEmUsoException;
import br.gov.df.pm.domain.exception.MarcaVeiculoNaoEncontradaException;
import br.gov.df.pm.domain.exception.NegocioException;
import br.gov.df.pm.domain.model.MarcaVeiculo;
import br.gov.df.pm.domain.model.TipoVeiculo;
import br.gov.df.pm.domain.repository.MarcaVeiculoRepository;

@Service
public class CadastroMarcaVeiculoService {
	
	private static final String MSG_MARCAVEICULO_EM_USO 
	= "Marca do veículo de código %d não pode ser removido, pois está em uso";
	
	@Autowired
	private CadastroTipoVeiculoService cadastroTipoVeiculo;
	
	@Autowired
	private MarcaVeiculoRepository marcaVeiculoRepository;

	@Transactional
	public MarcaVeiculo salvar(MarcaVeiculo marcaVeiculo) {
		
		marcaVeiculoRepository.detach(marcaVeiculo);
		
		Optional<MarcaVeiculo> marcaExiste = marcaVeiculoRepository
				.findByNomeAndTipoVeiculo(marcaVeiculo.getNome(), marcaVeiculo.getTipoVeiculo());
		
		if(marcaExiste.isPresent() && !marcaExiste.get().equals(marcaVeiculo)) {
			throw new NegocioException(
					String.format("Já existe uma marca de veículo cadastrada com este nome %s", 
						marcaVeiculo.getNome()));
		}
		
		Long tipoVeiculoId = marcaVeiculo.getTipoVeiculo().getId();	
		TipoVeiculo tipoVeiculo = cadastroTipoVeiculo.buscarOuFalhar(tipoVeiculoId);
		
		marcaVeiculo.setTipoVeiculo(tipoVeiculo);
		
		return marcaVeiculoRepository.save(marcaVeiculo);
	}
	
	@Transactional
	public void excluir(Long marcaVeiculoId) {
		
		try {
			marcaVeiculoRepository.deleteById(marcaVeiculoId);
			marcaVeiculoRepository.flush();
		} catch (EmptyResultDataAccessException e) {
			throw new MarcaVeiculoNaoEncontradaException(marcaVeiculoId);
			
		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(
					String.format(MSG_MARCAVEICULO_EM_USO, marcaVeiculoId));
		}
	}
	
	public MarcaVeiculo buscarOuFalhar(Long marcaVeiculoId) {
		return marcaVeiculoRepository.findById(marcaVeiculoId)
				.orElseThrow(() -> new MarcaVeiculoNaoEncontradaException(marcaVeiculoId));
				
	}
	
	
	@Transactional
	public void alterarStatus(Long[] marcaVeiculoIds, StatusMarcaVeiculo status) {
		
		//VERIFICA SE IDS EXISTEM, CASO NÃO EXISTE LANÇA ERRO
		for(int i=0; i < marcaVeiculoIds.length; i++) {
			buscarOuFalhar(marcaVeiculoIds[i]);
		}
		
		status.executar(marcaVeiculoIds, marcaVeiculoRepository);
	}

}
