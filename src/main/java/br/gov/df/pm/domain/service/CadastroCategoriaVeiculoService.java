package br.gov.df.pm.domain.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import br.gov.df.pm.domain.enumeration.StatusCategoriaVeiculo;
import br.gov.df.pm.domain.exception.CategoriaVeiculoNaoEncontradoException;
import br.gov.df.pm.domain.exception.EntidadeEmUsoException;
import br.gov.df.pm.domain.exception.NegocioException;
import br.gov.df.pm.domain.model.CategoriaVeiculo;
import br.gov.df.pm.domain.model.ModeloVeiculo;
import br.gov.df.pm.domain.repository.CategoriaVeiculoRepository;
import br.gov.df.pm.domain.repository.ModeloVeiculoRepository;

@Service
public class CadastroCategoriaVeiculoService {
	
	private static final String MSG_CATEGORIAVEICULO_EM_USO 
	= "Categoria de veículo de código %d não pode ser removido ou inativado, pois está em uso";
	
	
	@Autowired
	private CategoriaVeiculoRepository categoriaVeiculoRepository;
	
	@Autowired
	private ModeloVeiculoRepository modeloVeiculoRepository;

	public CategoriaVeiculo buscarOuFalhar(Long categoriaVeiculoId) {
		return categoriaVeiculoRepository.findById(categoriaVeiculoId)
				.orElseThrow(() -> new CategoriaVeiculoNaoEncontradoException(categoriaVeiculoId));
	}

	@Transactional
	public CategoriaVeiculo salvar(CategoriaVeiculo categoriaVeiculo) {
		categoriaVeiculoRepository.detach(categoriaVeiculo);
		
		Optional<CategoriaVeiculo> categoriaExiste = categoriaVeiculoRepository.
				findByNomeIgnoreCase(categoriaVeiculo.getNome());

		if(categoriaExiste.isPresent() && !categoriaExiste.get().equals(categoriaVeiculo)) {
			throw new NegocioException(
				String.format("Já existe uma categoria de veículo cadastrado com o nome %s", 
						categoriaVeiculo.getNome()));
		}
		
		if(categoriaVeiculo.getAtivo() != null && categoriaVeiculo.getAtivo() == 0) {
			
			List<ModeloVeiculo> emUso = modeloVeiculoRepository
										.verificaSeItemEmUso(categoriaVeiculo.getId());
			
			if(!emUso.isEmpty()) {
				throw new EntidadeEmUsoException(
						String.format(MSG_CATEGORIAVEICULO_EM_USO , categoriaVeiculo.getId()));
			}
			
		}
		
		return categoriaVeiculoRepository.save(categoriaVeiculo);
	}

	@Transactional
	public void excluir(Long categoriaVeiculoId) {

		try {
			categoriaVeiculoRepository.deleteById(categoriaVeiculoId);
			categoriaVeiculoRepository.flush();
		} catch (EmptyResultDataAccessException e) {
			throw new CategoriaVeiculoNaoEncontradoException(categoriaVeiculoId);
			
		} catch (DataIntegrityViolationException e) {	
			throw new EntidadeEmUsoException(
					String.format(MSG_CATEGORIAVEICULO_EM_USO , categoriaVeiculoId));
		}
		
	}

	@Transactional
	public void alterarStatus(Long[] categoriaVeiculoIds, StatusCategoriaVeiculo status) {
		
		//VERIFICA SE IDS EXISTEM, CASO NÃO EXISTE LANÇA ERRO
		for(int i=0; i < categoriaVeiculoIds.length; i++) {
			buscarOuFalhar(categoriaVeiculoIds[i]);
		}
		
		status.executar(categoriaVeiculoIds, categoriaVeiculoRepository);
	}

}
