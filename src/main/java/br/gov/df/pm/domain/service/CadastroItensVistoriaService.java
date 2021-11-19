package br.gov.df.pm.domain.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import br.gov.df.pm.domain.enumeration.StatusItensVistoria;
import br.gov.df.pm.domain.exception.EntidadeEmUsoException;
import br.gov.df.pm.domain.exception.ItensVistoriaNaoEncontradoException;
import br.gov.df.pm.domain.exception.MarcaVeiculoNaoEncontradaException;
import br.gov.df.pm.domain.exception.NegocioException;
import br.gov.df.pm.domain.model.ItensVistoria;
import br.gov.df.pm.domain.model.TipoVistoria;
import br.gov.df.pm.domain.model.VistoriaViaturaItensVistoria;
import br.gov.df.pm.domain.repository.ItensVistoriaRepository;
import br.gov.df.pm.domain.repository.VistoriaViaturaItensVistoriaRepository;

@Service
public class CadastroItensVistoriaService {
	
	private static final String MSG_ITENSVISTORIA_EM_USO 
	= "Itens vistoria de código %d não pode ser removido ou inativado, pois está em uso";
	
	@Autowired
	private CadastroTipoVistoriaService cadastroTipoVistoria;
	
	@Autowired
	private ItensVistoriaRepository itensVistoriaRepository;
	
	@Autowired
	private VistoriaViaturaItensVistoriaRepository vistoriaViaturaItensVistoriaRepository;
	
	
	@Transactional
	public ItensVistoria salvar(ItensVistoria itensVistoria) {
		
		itensVistoriaRepository.detach(itensVistoria);
		
		Optional<ItensVistoria> itensExiste = itensVistoriaRepository
				.findByNomeAndTipoVistoria(itensVistoria.getNome(), itensVistoria.getTipoVistoria());
		
		if(itensExiste.isPresent() && !itensExiste.get().equals(itensVistoria)) {
			throw new NegocioException(
					String.format("Já existe um item de vistoria cadastrado com este nome %s", 
						itensVistoria.getNome()));
		}
		
		if(itensVistoria.getAtivo() != null && itensVistoria.getAtivo() == 0) {
			
			List<VistoriaViaturaItensVistoria> emUso = vistoriaViaturaItensVistoriaRepository
										.verificaSeItemVistoriaEmUso(itensVistoria.getId());
			
			if(!emUso.isEmpty()) {
				throw new EntidadeEmUsoException(
						String.format(MSG_ITENSVISTORIA_EM_USO , itensVistoria.getId()));
			}
			
		}
		
		Long tipoVistoriaId = itensVistoria.getTipoVistoria().getId();	
		TipoVistoria tipoVistoria = cadastroTipoVistoria.buscarOuFalhar(tipoVistoriaId);
		
		itensVistoria.setTipoVistoria(tipoVistoria);
		
		return itensVistoriaRepository.save(itensVistoria);
	}
	
	@Transactional
	public void excluir(Long itensVistoriaId) {
		
		try {
			itensVistoriaRepository.deleteById(itensVistoriaId);
			itensVistoriaRepository.flush();
		} catch (EmptyResultDataAccessException e) {
			throw new ItensVistoriaNaoEncontradoException(itensVistoriaId);
			
		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(
					String.format(MSG_ITENSVISTORIA_EM_USO, itensVistoriaId));
		}
	}
	
	public ItensVistoria buscarOuFalhar(Long itensVistoriaId) {
		return itensVistoriaRepository.findById(itensVistoriaId)
				.orElseThrow(() -> new MarcaVeiculoNaoEncontradaException(itensVistoriaId));
				
	}
	
	
	@Transactional
	public void alterarStatus(Long[] itensVistoriaIds, StatusItensVistoria status) {
		
		//VERIFICA SE IDS EXISTEM, CASO NÃO EXISTE LANÇA ERRO
		for(int i=0; i < itensVistoriaIds.length; i++) {
			buscarOuFalhar(itensVistoriaIds[i]);
		}
		
		status.executar(itensVistoriaIds, itensVistoriaRepository);
	}

}
