package br.gov.df.pm.domain.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import br.gov.df.pm.domain.enumeration.StatusMotivoBaixaViatura;
import br.gov.df.pm.domain.exception.EntidadeEmUsoException;
import br.gov.df.pm.domain.exception.MotivoBaixaViaturaNaoEncontradoException;
import br.gov.df.pm.domain.exception.NegocioException;
import br.gov.df.pm.domain.model.HistoricoBaixaViatura;
import br.gov.df.pm.domain.model.MotivoBaixaViatura;
import br.gov.df.pm.domain.repository.HistoricoBaixaViaturaRepository;
import br.gov.df.pm.domain.repository.MotivoBaixaViaturaRepository;

@Service
public class CadastroMotivoBaixaViaturaService {
	
	private static final String MSG_MOTIVOBAIXAVIATURA_EM_USO 
	= "Motivo da baixa da viatura de código %d não pode ser removido ou inativado, pois está em uso";

	
	@Autowired
	private MotivoBaixaViaturaRepository motivoBaixaViaturaRepository;
	
	@Autowired
	private HistoricoBaixaViaturaRepository hitoricoBaixaViaturaRepository;

	public MotivoBaixaViatura buscarOuFalhar(Long motivoBaixaViaturaId) {
		return motivoBaixaViaturaRepository.findById(motivoBaixaViaturaId)
				.orElseThrow(() -> new MotivoBaixaViaturaNaoEncontradoException(motivoBaixaViaturaId));
	}

	@Transactional
	public MotivoBaixaViatura salvar(MotivoBaixaViatura motivoBaixaViatura) {
		motivoBaixaViaturaRepository.detach(motivoBaixaViatura);
		
		Optional<MotivoBaixaViatura> motivoExiste = motivoBaixaViaturaRepository.
				findByNomeIgnoreCase(motivoBaixaViatura.getNome());

		if(motivoExiste.isPresent() && !motivoExiste.get().equals(motivoBaixaViatura)) {
			throw new NegocioException(
				String.format("Já existe um motivo de baixa de viatura cadastrado com o nome %s", 
						motivoBaixaViatura.getNome()));
		}
		
		if(motivoBaixaViatura.getAtivo() != null && motivoBaixaViatura.getAtivo() == 0) {
			
			List<HistoricoBaixaViatura> emUso = hitoricoBaixaViaturaRepository
										.verificaSeItemEmUso(motivoBaixaViatura.getId());
			
			if(!emUso.isEmpty()) {
				throw new EntidadeEmUsoException(
						String.format(MSG_MOTIVOBAIXAVIATURA_EM_USO , motivoBaixaViatura.getId()));
			}
			
		}
		
		return motivoBaixaViaturaRepository.save(motivoBaixaViatura);
	}

	@Transactional
	public void excluir(Long motivoBaixaViaturaId) {

		try {
			motivoBaixaViaturaRepository.deleteById(motivoBaixaViaturaId);
			motivoBaixaViaturaRepository.flush();
		} catch (EmptyResultDataAccessException e) {
			throw new MotivoBaixaViaturaNaoEncontradoException(motivoBaixaViaturaId);
			
		} catch (DataIntegrityViolationException e) {	
			throw new EntidadeEmUsoException(
					String.format(MSG_MOTIVOBAIXAVIATURA_EM_USO , motivoBaixaViaturaId));
		}

		
		
	}

	@Transactional
	public void alterarStatus(Long[] motivoBaixaViaturaIds, StatusMotivoBaixaViatura status) {
		
		//VERIFICA SE IDS EXISTEM, CASO NÃO EXISTE LANÇA ERRO
		for(int i=0; i < motivoBaixaViaturaIds.length; i++) {
			buscarOuFalhar(motivoBaixaViaturaIds[i]);
		}
		
		status.executar(motivoBaixaViaturaIds, motivoBaixaViaturaRepository);
	}

}
