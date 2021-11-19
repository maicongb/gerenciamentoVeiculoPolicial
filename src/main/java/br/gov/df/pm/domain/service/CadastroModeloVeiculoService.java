package br.gov.df.pm.domain.service;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import br.gov.df.pm.domain.enumeration.StatusModeloVeiculo;
import br.gov.df.pm.domain.exception.EntidadeEmUsoException;
import br.gov.df.pm.domain.exception.ModeloVeiculoNaoEncontradoException;
import br.gov.df.pm.domain.exception.NegocioException;
import br.gov.df.pm.domain.model.CategoriaVeiculo;
import br.gov.df.pm.domain.model.MarcaVeiculo;
import br.gov.df.pm.domain.model.ModeloVeiculo;
import br.gov.df.pm.domain.repository.ModeloVeiculoRepository;

@Service
public class CadastroModeloVeiculoService {
	
	private static final String MSG_MODELOVEICULO_EM_USO 
	= "Modelo do veículo de código %d não pode ser removido, pois está em uso";
	
	@Autowired
	private ModeloVeiculoRepository modeloVeiculoRepository;
	
	@Autowired
	private CadastroMarcaVeiculoService cadastroMarcaVeiculo;
	
	@Autowired
	private CadastroCategoriaVeiculoService cadastroCategoriaVeiculo;

	@Transactional
	public ModeloVeiculo salvar(ModeloVeiculo modeloVeiculo) {
		
		modeloVeiculoRepository.detach(modeloVeiculo);
		
		Optional<ModeloVeiculo> modeloExiste = modeloVeiculoRepository
				.findByNomeAndMarcaVeiculo(modeloVeiculo.getNome(), modeloVeiculo.getMarcaVeiculo());
		
		if(modeloExiste.isPresent() && !modeloExiste.get().equals(modeloVeiculo)) {
			throw new NegocioException(
					String.format("Já existe uma modelo de veículo cadastrado com este nome %s", 
						modeloVeiculo.getNome()));
		}
		
		Long marcaVeiculoId = modeloVeiculo.getMarcaVeiculo().getId();		
		MarcaVeiculo marcaVeiculo = cadastroMarcaVeiculo.buscarOuFalhar(marcaVeiculoId);
		
		Long categoriaVeiculoId = modeloVeiculo.getCategoriaVeiculo().getId();
		CategoriaVeiculo categoriaVeiculo = cadastroCategoriaVeiculo.buscarOuFalhar(categoriaVeiculoId);
		
		modeloVeiculo.setMarcaVeiculo(marcaVeiculo);
		modeloVeiculo.setCategoriaVeiculo(categoriaVeiculo);
		
		return modeloVeiculoRepository.save(modeloVeiculo);
	}
	
	@Transactional
	public void excluir(Long modeloVeiculoId) {
		
		try {
			modeloVeiculoRepository.deleteById(modeloVeiculoId);
			modeloVeiculoRepository.flush();
		} catch (EmptyResultDataAccessException e) {
			throw new ModeloVeiculoNaoEncontradoException(modeloVeiculoId);
			
		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(
					String.format(MSG_MODELOVEICULO_EM_USO, modeloVeiculoId));
		}
	}
	
	public ModeloVeiculo buscarOuFalhar(Long modeloVeiculoId) {
		return modeloVeiculoRepository.findById(modeloVeiculoId)
				.orElseThrow(() -> new ModeloVeiculoNaoEncontradoException(modeloVeiculoId));
				
	}
	
	
	@Transactional
	public void alterarStatus(Long[] modeloVeiculoIds, StatusModeloVeiculo status) {
		
		//VERIFICA SE IDS EXISTEM, CASO NÃO EXISTE LANÇA ERRO
		for(int i=0; i < modeloVeiculoIds.length; i++) {
			buscarOuFalhar(modeloVeiculoIds[i]);
		}
		
		status.executar(modeloVeiculoIds, modeloVeiculoRepository);
	}

}
