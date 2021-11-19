package br.gov.df.pm.domain.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import br.gov.df.pm.domain.enumeration.StatusTipoVeiculo;
import br.gov.df.pm.domain.exception.EntidadeEmUsoException;
import br.gov.df.pm.domain.exception.NegocioException;
import br.gov.df.pm.domain.exception.TipoVeiculoNaoEncontradoException;
import br.gov.df.pm.domain.model.MarcaVeiculo;
import br.gov.df.pm.domain.model.TipoVeiculo;
import br.gov.df.pm.domain.repository.MarcaVeiculoRepository;
import br.gov.df.pm.domain.repository.TipoVeiculoRepository;

@Service
public class CadastroTipoVeiculoService {
	
	private static final String MSG_TIPOVEICULO_EM_USO 
	= "Tipo de veículo de código %d não pode ser removido ou inativado, pois está em uso";

	
	@Autowired
	private TipoVeiculoRepository tipoVeiculoRepository;
	
	@Autowired
	private MarcaVeiculoRepository marcaVeiculoRepository;

	public TipoVeiculo buscarOuFalhar(Long tipoVeiculoId) {
		return tipoVeiculoRepository.findById(tipoVeiculoId)
				.orElseThrow(() -> new TipoVeiculoNaoEncontradoException(tipoVeiculoId));
	}

	@Transactional
	public TipoVeiculo salvar(TipoVeiculo tipoVeiculo) {
		tipoVeiculoRepository.detach(tipoVeiculo);
		
		Optional<TipoVeiculo> tipoExiste = tipoVeiculoRepository.
				findByNomeIgnoreCase(tipoVeiculo.getNome());

		if(tipoExiste.isPresent() && !tipoExiste.get().equals(tipoVeiculo)) {
			throw new NegocioException(
				String.format("Já existe um tipo de veículo cadastrado com o nome %s", 
						tipoVeiculo.getNome()));
		}
		
		if(tipoVeiculo.getAtivo() != null && tipoVeiculo.getAtivo() == 0) {
			
			List<MarcaVeiculo> emUso = marcaVeiculoRepository
										.verificaSeItemEmUso(tipoVeiculo.getId());
			
			if(!emUso.isEmpty()) {
				throw new EntidadeEmUsoException(
						String.format(MSG_TIPOVEICULO_EM_USO , tipoVeiculo.getId()));
			}
			
		}
		
	
		return tipoVeiculoRepository.save(tipoVeiculo);
	}

	@Transactional
	public void excluir(Long tipoVeiculoId) {

		try {
			tipoVeiculoRepository.deleteById(tipoVeiculoId);
			tipoVeiculoRepository.flush();
		} catch (EmptyResultDataAccessException e) {
			throw new TipoVeiculoNaoEncontradoException(tipoVeiculoId);
			
		} catch (DataIntegrityViolationException e) {	
			throw new EntidadeEmUsoException(
					String.format(MSG_TIPOVEICULO_EM_USO , tipoVeiculoId));
		}
		
	}

	@Transactional
	public void alterarStatus(Long[] tipoVeiculoIds, StatusTipoVeiculo status) {
		
		//VERIFICA SE IDS EXISTEM, CASO NÃO EXISTE LANÇA ERRO
		for(int i=0; i < tipoVeiculoIds.length; i++) {
			buscarOuFalhar(tipoVeiculoIds[i]);
		}
		
		status.executar(tipoVeiculoIds, tipoVeiculoRepository);
	}

}
