package br.gov.df.pm.domain.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import br.gov.df.pm.domain.enumeration.StatusTipoViatura;
import br.gov.df.pm.domain.exception.EntidadeEmUsoException;
import br.gov.df.pm.domain.exception.NegocioException;
import br.gov.df.pm.domain.exception.TipoViaturaNaoEncontradoException;
import br.gov.df.pm.domain.model.TipoViatura;
import br.gov.df.pm.domain.model.Viatura;
import br.gov.df.pm.domain.repository.TipoViaturaRepository;
import br.gov.df.pm.domain.repository.ViaturaRepository;

@Service
public class CadastroTipoViaturaService {
	
	private static final String MSG_TIPOVIATURA_EM_USO 
	= "Tipo de viatura de código %d não pode ser removido ou inativado, pois está em uso";

	
	@Autowired
	private TipoViaturaRepository tipoViaturaRepository;
	
	@Autowired
	private ViaturaRepository viaturaRepository;

	public TipoViatura buscarOuFalhar(Long tipoViaturaId) {
		return tipoViaturaRepository.findById(tipoViaturaId)
				.orElseThrow(() -> new TipoViaturaNaoEncontradoException(tipoViaturaId));
	}

	@Transactional
	public TipoViatura salvar(TipoViatura tipoViatura) {
		tipoViaturaRepository.detach(tipoViatura);
		
		Optional<TipoViatura> tipoExiste = tipoViaturaRepository.
				findByNomeIgnoreCase(tipoViatura.getNome());

		if(tipoExiste.isPresent() && !tipoExiste.get().equals(tipoViatura)) {
			throw new NegocioException(
				String.format("Já existe um tipo de viatura cadastrado com o nome %s", 
						tipoViatura.getNome()));
		}
		
		if(tipoViatura.getAtivo() != null && tipoViatura.getAtivo() == 0) {
			
			List<Viatura> emUso = viaturaRepository
											.verificaSeItemTipoViaturaEmUso(tipoViatura.getId());
			
			if(!emUso.isEmpty()) {
				throw new EntidadeEmUsoException(
						String.format(MSG_TIPOVIATURA_EM_USO , tipoViatura.getId()));
			}
			
		}
		
		return tipoViaturaRepository.save(tipoViatura);
	}

	@Transactional
	public void excluir(Long tipoViaturaId) {

		try {
			tipoViaturaRepository.deleteById(tipoViaturaId);
			tipoViaturaRepository.flush();
		} catch (EmptyResultDataAccessException e) {
			throw new TipoViaturaNaoEncontradoException(tipoViaturaId);
			
		} catch (DataIntegrityViolationException e) {	
			throw new EntidadeEmUsoException(
					String.format(MSG_TIPOVIATURA_EM_USO , tipoViaturaId));
		}

		
		
	}

	@Transactional
	public void alterarStatus(Long[] tipoViaturaIds, StatusTipoViatura status) {
		
		//VERIFICA SE IDS EXISTEM, CASO NÃO EXISTE LANÇA ERRO
		for(int i=0; i < tipoViaturaIds.length; i++) {
			buscarOuFalhar(tipoViaturaIds[i]);
		}
		
		status.executar(tipoViaturaIds, tipoViaturaRepository);
	}

}
