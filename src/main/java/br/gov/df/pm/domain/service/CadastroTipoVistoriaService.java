package br.gov.df.pm.domain.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import br.gov.df.pm.domain.enumeration.StatusTipoVistoria;
import br.gov.df.pm.domain.exception.EntidadeEmUsoException;
import br.gov.df.pm.domain.exception.NegocioException;
import br.gov.df.pm.domain.exception.TipoVistoriaNaoEncontradaException;
import br.gov.df.pm.domain.model.TipoVistoria;
import br.gov.df.pm.domain.model.VistoriaViatura;
import br.gov.df.pm.domain.repository.TipoVistoriaRepository;
import br.gov.df.pm.domain.repository.VistoriaViaturaRepository;

@Service
public class CadastroTipoVistoriaService {
	
	private static final String MSG_TIPOVISTORIA_EM_USO 
	= "Tipo de vistoria de código %d não pode ser removido ou inativado, pois está em uso";

	
	@Autowired
	private TipoVistoriaRepository tipoVistoriaRepository;
	
	@Autowired
	private VistoriaViaturaRepository vistoriaViaturaRepository;

	public TipoVistoria buscarOuFalhar(Long tipoVistoriaId) {
		return tipoVistoriaRepository.findById(tipoVistoriaId)
				.orElseThrow(() -> new TipoVistoriaNaoEncontradaException(tipoVistoriaId));
	}

	@Transactional
	public TipoVistoria salvar(TipoVistoria tipoVistoria) {
		tipoVistoriaRepository.detach(tipoVistoria);
		
		Optional<TipoVistoria> tipoExiste = tipoVistoriaRepository.
				findByNomeIgnoreCase(tipoVistoria.getNome());

		if(tipoExiste.isPresent() && !tipoExiste.get().equals(tipoVistoria)) {
			throw new NegocioException(
				String.format("Já existe um tipo de vistoria cadastrada com o nome %s", 
						tipoVistoria.getNome()));
		}
		
		
		if(tipoVistoria.getAtivo() != null && tipoVistoria.getAtivo() == 0) {
			
			List<VistoriaViatura> emUso = vistoriaViaturaRepository
											.verificaSeItemTipoVistoriaEmUso(tipoVistoria.getId());
			
			if(!emUso.isEmpty()) {
				throw new EntidadeEmUsoException(
						String.format(MSG_TIPOVISTORIA_EM_USO , tipoVistoria.getId()));
			}
			
		}
		
		return tipoVistoriaRepository.save(tipoVistoria);
	}

	@Transactional
	public void excluir(Long tipoVistoriaId) {

		try {
			tipoVistoriaRepository.deleteById(tipoVistoriaId);
			tipoVistoriaRepository.flush();
		} catch (EmptyResultDataAccessException e) {
			throw new TipoVistoriaNaoEncontradaException(tipoVistoriaId);
			
		} catch (DataIntegrityViolationException e) {	
			throw new EntidadeEmUsoException(
					String.format(MSG_TIPOVISTORIA_EM_USO , tipoVistoriaId));
		}

		
		
	}

	@Transactional
	public void alterarStatus(Long[] tipoVistoriaIds, StatusTipoVistoria status) {
		
		//VERIFICA SE IDS EXISTEM, CASO NÃO EXISTE LANÇA ERRO
		for(int i=0; i < tipoVistoriaIds.length; i++) {
			buscarOuFalhar(tipoVistoriaIds[i]);
		}
		
		status.executar(tipoVistoriaIds, tipoVistoriaRepository);
	}

}
