package br.gov.df.pm.domain.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import br.gov.df.pm.domain.enumeration.StatusTipoCombustivel;
import br.gov.df.pm.domain.exception.EntidadeEmUsoException;
import br.gov.df.pm.domain.exception.NegocioException;
import br.gov.df.pm.domain.exception.TipoCombustivelNaoEncontradoException;
import br.gov.df.pm.domain.model.TipoCombustivel;
import br.gov.df.pm.domain.model.Viatura;
import br.gov.df.pm.domain.repository.TipoCombustivelRepository;
import br.gov.df.pm.domain.repository.ViaturaRepository;

@Service
public class CadastroTipoCombustivelService {
	
	private static final String MSG_TIPOCOMBUSTIVEL_EM_USO 
	= "Tipo de combustível de código %d não pode ser removido ou inativado, pois está em uso";

	
	@Autowired
	private TipoCombustivelRepository tipoCombustivelRepository;
	
	@Autowired
	private ViaturaRepository viaturaRepository;

	public TipoCombustivel buscarOuFalhar(Long tipoCombustivelId) {
		return tipoCombustivelRepository.findById(tipoCombustivelId)
				.orElseThrow(() -> new TipoCombustivelNaoEncontradoException(tipoCombustivelId));
	}

	@Transactional
	public TipoCombustivel salvar(TipoCombustivel tipoCombustivel) {
		tipoCombustivelRepository.detach(tipoCombustivel);
		
		Optional<TipoCombustivel> motivoExiste = tipoCombustivelRepository.
				findByNomeIgnoreCase(tipoCombustivel.getNome());

		if(motivoExiste.isPresent() && !motivoExiste.get().equals(tipoCombustivel)) {
			throw new NegocioException(
				String.format("Já existe um tipo de combustível cadastrado com o nome %s", 
						tipoCombustivel.getNome()));
		}
		
		if(tipoCombustivel.getAtivo() != null && tipoCombustivel.getAtivo() == 0) {
			
			List<Viatura> emUso = viaturaRepository
											.verificaSeItemTipoCombustivelEmUso(tipoCombustivel.getId());
			
			if(!emUso.isEmpty()) {
				throw new EntidadeEmUsoException(
						String.format(MSG_TIPOCOMBUSTIVEL_EM_USO , tipoCombustivel.getId()));
			}
			
		}
		
		return tipoCombustivelRepository.save(tipoCombustivel);
	}

	@Transactional
	public void excluir(Long tipoCombustivelId) {

		try {
			tipoCombustivelRepository.deleteById(tipoCombustivelId);
			tipoCombustivelRepository.flush();
		} catch (EmptyResultDataAccessException e) {
			throw new TipoCombustivelNaoEncontradoException(tipoCombustivelId);
			
		} catch (DataIntegrityViolationException e) {	
			throw new EntidadeEmUsoException(
					String.format(MSG_TIPOCOMBUSTIVEL_EM_USO , tipoCombustivelId));
		}

		
		
	}

	@Transactional
	public void alterarStatus(Long[] tipoCombustivelIds, StatusTipoCombustivel status) {
		
		//VERIFICA SE IDS EXISTEM, CASO NÃO EXISTE LANÇA ERRO
		for(int i=0; i < tipoCombustivelIds.length; i++) {
			buscarOuFalhar(tipoCombustivelIds[i]);
		}
		
		status.executar(tipoCombustivelIds, tipoCombustivelRepository);
	}

}
