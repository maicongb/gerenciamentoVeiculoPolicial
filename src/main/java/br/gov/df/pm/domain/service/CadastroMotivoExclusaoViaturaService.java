package br.gov.df.pm.domain.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import br.gov.df.pm.domain.enumeration.StatusMotivoExclusaoViatura;
import br.gov.df.pm.domain.exception.EntidadeEmUsoException;
import br.gov.df.pm.domain.exception.MotivoExclusaoViaturaNaoEncontradoException;
import br.gov.df.pm.domain.exception.NegocioException;
import br.gov.df.pm.domain.model.MotivoExclusaoViatura;
import br.gov.df.pm.domain.model.Viatura;
import br.gov.df.pm.domain.repository.MotivoExclusaoViaturaRepository;
import br.gov.df.pm.domain.repository.ViaturaRepository;

@Service
public class CadastroMotivoExclusaoViaturaService {
	
	private static final String MSG_MOTIVOEXCLUSAOVIATURA_EM_USO 
	= "Motivo da exclusão da viatura de código %d não pode ser removido ou inativado, pois está em uso";

	
	@Autowired
	private MotivoExclusaoViaturaRepository motivoExclusaoViaturaRepository;
	
	@Autowired
	private ViaturaRepository viaturaRepository;

	public MotivoExclusaoViatura buscarOuFalhar(Long motivoExclusaViaturaId) {
		return motivoExclusaoViaturaRepository.findById(motivoExclusaViaturaId)
				.orElseThrow(() -> new MotivoExclusaoViaturaNaoEncontradoException(motivoExclusaViaturaId));
	}

	@Transactional
	public MotivoExclusaoViatura salvar(MotivoExclusaoViatura motivoExclusaoViatura) {
		motivoExclusaoViaturaRepository.detach(motivoExclusaoViatura);
		
		Optional<MotivoExclusaoViatura> motivoExiste = motivoExclusaoViaturaRepository.
				findByNomeIgnoreCase(motivoExclusaoViatura.getNome());

		if(motivoExiste.isPresent() && !motivoExiste.get().equals(motivoExclusaoViatura)) {
			throw new NegocioException(
				String.format("Já existe um motivo de exclusão de viatura cadastrado com o nome %s", 
						motivoExclusaoViatura.getNome()));
		}
		
		
		if(motivoExclusaoViatura.getAtivo() != null && motivoExclusaoViatura.getAtivo() == 0) {
			
			List<Viatura> emUso = viaturaRepository
											.verificaSeItemMotivoExclusaoEmUso(motivoExclusaoViatura.getId());
			
			if(!emUso.isEmpty()) {
				throw new EntidadeEmUsoException(
						String.format(MSG_MOTIVOEXCLUSAOVIATURA_EM_USO , motivoExclusaoViatura.getId()));
			}
			
		}
		
		return motivoExclusaoViaturaRepository.save(motivoExclusaoViatura);
	}

	@Transactional
	public void excluir(Long motivoExclusaoViaturaId) {

		try {
			motivoExclusaoViaturaRepository.deleteById(motivoExclusaoViaturaId);
			motivoExclusaoViaturaRepository.flush();
		} catch (EmptyResultDataAccessException e) {
			throw new MotivoExclusaoViaturaNaoEncontradoException(motivoExclusaoViaturaId);
			
		} catch (DataIntegrityViolationException e) {	
			throw new EntidadeEmUsoException(
					String.format(MSG_MOTIVOEXCLUSAOVIATURA_EM_USO , motivoExclusaoViaturaId));
		}

		
		
	}

	@Transactional
	public void alterarStatus(Long[] motivoExclusaoViaturaIds, StatusMotivoExclusaoViatura status) {
		
		//VERIFICA SE IDS EXISTEM, CASO NÃO EXISTE LANÇA ERRO
		for(int i=0; i < motivoExclusaoViaturaIds.length; i++) {
			buscarOuFalhar(motivoExclusaoViaturaIds[i]);
		}
		
		status.executar(motivoExclusaoViaturaIds, motivoExclusaoViaturaRepository);
	}

}
