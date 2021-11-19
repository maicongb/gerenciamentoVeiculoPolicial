package br.gov.df.pm.domain.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import br.gov.df.pm.domain.enumeration.StatusMotivoTransferencia;
import br.gov.df.pm.domain.exception.EntidadeEmUsoException;
import br.gov.df.pm.domain.exception.MotivoTransferenciaViaturaNaoEncontradoException;
import br.gov.df.pm.domain.exception.NegocioException;
import br.gov.df.pm.domain.model.MotivoTransferenciaViatura;
import br.gov.df.pm.domain.model.ViaturaUpm;
import br.gov.df.pm.domain.repository.MotivoTransferenciaViaturaRepository;
import br.gov.df.pm.domain.repository.ViaturaUpmRepository;

@Service
public class CadastroMotivoTransferenciaViaturaService {
	
	private static final String MSG_MOTIVOTRANSFERENCIAVIATURA_EM_USO 
	= "Motivo de transferência de código %d não pode ser removido, pois está em uso";
	
	@Autowired
	private MotivoTransferenciaViaturaRepository motivoTransferenciaViaturaRepository;
	
	@Autowired
	private ViaturaUpmRepository viaturaUpmRepository;

	public MotivoTransferenciaViatura buscarOuFalhar(Long motivoId) {
		return motivoTransferenciaViaturaRepository.findById(motivoId)
				.orElseThrow(() -> new MotivoTransferenciaViaturaNaoEncontradoException(motivoId));
	}

	@Transactional
	public MotivoTransferenciaViatura salvar(MotivoTransferenciaViatura motivoTransferenciaViatura) {
		
		motivoTransferenciaViaturaRepository.detach(motivoTransferenciaViatura);
		
		Optional<MotivoTransferenciaViatura> motivoExiste = motivoTransferenciaViaturaRepository.
								findByNomeIgnoreCase(motivoTransferenciaViatura.getNome());
		
		if(motivoExiste.isPresent() && !motivoExiste.get().equals(motivoTransferenciaViatura)) {
			throw new NegocioException(
					String.format("Já existe um motivo cadastrado com o nome %s", 
							motivoTransferenciaViatura.getNome()));
		}
		
		if(motivoTransferenciaViatura.getAtivo() != null && motivoTransferenciaViatura.getAtivo() == 0) {
			
			List<ViaturaUpm> emUso = viaturaUpmRepository
											.verificaSeItemMotivoExclusaoEmUso(motivoTransferenciaViatura.getId());
			
			if(!emUso.isEmpty()) {
				throw new EntidadeEmUsoException(
						String.format(MSG_MOTIVOTRANSFERENCIAVIATURA_EM_USO , motivoTransferenciaViatura.getId()));
			}
			
		}
		
		return motivoTransferenciaViaturaRepository.save(motivoTransferenciaViatura);
	}

	@Transactional
	public void excluir(Long motivoId) {

		try {
			motivoTransferenciaViaturaRepository.deleteById(motivoId);
			motivoTransferenciaViaturaRepository.flush();
		} catch (EmptyResultDataAccessException e) {
			throw new MotivoTransferenciaViaturaNaoEncontradoException(motivoId);
			
		} catch (DataIntegrityViolationException e) {	
			throw new EntidadeEmUsoException(
					String.format(MSG_MOTIVOTRANSFERENCIAVIATURA_EM_USO , motivoId));
		}

		
		
	}

	@Transactional
	public void alterarStatus(Long[] motivoTransferenciaIds, StatusMotivoTransferencia status) {
		
		//VERIFICA SE IDS EXISTEM, CASO NÃO EXISTE LANÇA ERRO
		for(int i=0; i < motivoTransferenciaIds.length; i++) {
			buscarOuFalhar(motivoTransferenciaIds[i]);
		}
		
		status.executar(motivoTransferenciaIds, motivoTransferenciaViaturaRepository);
		
	}

}
