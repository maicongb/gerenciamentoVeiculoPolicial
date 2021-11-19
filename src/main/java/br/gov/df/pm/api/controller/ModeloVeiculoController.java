package br.gov.df.pm.api.controller;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.gov.df.pm.api.ResourceUriHelper;
import br.gov.df.pm.api.assembler.ModeloVeiculoInputDisassembler;
import br.gov.df.pm.api.assembler.ModeloVeiculoModelAssembler;
import br.gov.df.pm.api.model.ModeloVeiculoModel;
import br.gov.df.pm.api.model.input.AtivarDesativarModeloVeiculoInput;
import br.gov.df.pm.api.model.input.ModeloVeiculoInput;
import br.gov.df.pm.core.data.PageableTranslator;
import br.gov.df.pm.domain.exception.CategoriaVeiculoNaoEncontradoException;
import br.gov.df.pm.domain.exception.MarcaVeiculoNaoEncontradaException;
import br.gov.df.pm.domain.exception.NegocioException;
import br.gov.df.pm.domain.model.ModeloVeiculo;
import br.gov.df.pm.domain.repository.ModeloVeiculoRepository;
import br.gov.df.pm.domain.repository.filter.ModeloVeiculoFilter;
import br.gov.df.pm.domain.service.CadastroModeloVeiculoService;
import br.gov.df.pm.infrastructure.repository.spec.ModeloVeiculoSpecs;

@RestController
@RequestMapping(path = "/modelo-veiculo", produces = MediaType.APPLICATION_JSON_VALUE)
public class ModeloVeiculoController {
	
	@Autowired
	private CadastroModeloVeiculoService cadastroModeloVeiculo;
	
	@Autowired
	private ModeloVeiculoModelAssembler modeloVeiculoModelAssembler;
	
	@Autowired
	private ModeloVeiculoInputDisassembler modeloVeiculoInputDisassembler;
	
	@Autowired
	private ModeloVeiculoRepository modeloVeiculoRepository;
	
	@GetMapping
	public Page<ModeloVeiculoModel> listar(ModeloVeiculoFilter filtro,
			@PageableDefault(size=10) Pageable pageable) {
		
		pageable = traduzirPageable(pageable);
		
		Page<ModeloVeiculo> modeloVeiculoPage = 
				modeloVeiculoRepository.findAll(ModeloVeiculoSpecs.usandoFiltro(filtro), pageable);
		
		List<ModeloVeiculoModel> modeloVeiculoModel = modeloVeiculoModelAssembler
				.toCollectionModel(modeloVeiculoPage.getContent());
		
		Page<ModeloVeiculoModel> modeloVeiculoModelPage = new PageImpl<>(modeloVeiculoModel, 
				pageable, modeloVeiculoPage.getTotalElements());
		
		return modeloVeiculoModelPage;
	}
	
	@GetMapping("/{modeloVeiculoId}")
	public ModeloVeiculoModel buscar(@PathVariable Long modeloVeiculoId) {
		ModeloVeiculo modeloVeiculo = cadastroModeloVeiculo.buscarOuFalhar(modeloVeiculoId);
		
		return modeloVeiculoModelAssembler.toModel(modeloVeiculo);
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ModeloVeiculoModel adicionar(@RequestBody @Valid ModeloVeiculoInput modeloVeiculoInput) {
		
		try {
			ModeloVeiculo modeloVeiculo = modeloVeiculoInputDisassembler
					.toDomainObject(modeloVeiculoInput);
			
			modeloVeiculo = cadastroModeloVeiculo.salvar(modeloVeiculo);
			
			ModeloVeiculoModel modeloVeiculoModel = modeloVeiculoModelAssembler.toModel(modeloVeiculo);
			
			//ADICIONAR A URI http://localhost:8080/modelo-veiculo/1 NO HEADER
			ResourceUriHelper.addUriInResponseHeader(modeloVeiculoModel.getId());
			
			return modeloVeiculoModel;
			
		} catch (MarcaVeiculoNaoEncontradaException | CategoriaVeiculoNaoEncontradoException e) {
			throw new NegocioException(e.getMessage());
		}
		
	}
	
	@PutMapping("/{modeloVeiculoId}")
	public ModeloVeiculoModel atualizar(@RequestBody @Valid ModeloVeiculoInput modeloVeiculoInput,
				@PathVariable Long modeloVeiculoId) {
		
		try {
			
			ModeloVeiculo modeloVeiculoAtual = cadastroModeloVeiculo
					.buscarOuFalhar(modeloVeiculoId);
			
			modeloVeiculoInputDisassembler.copyToDomainObject(modeloVeiculoInput, modeloVeiculoAtual);
			
			modeloVeiculoAtual = cadastroModeloVeiculo.salvar(modeloVeiculoAtual);
			
			return modeloVeiculoModelAssembler.toModel(modeloVeiculoAtual);
			
		} catch (MarcaVeiculoNaoEncontradaException | CategoriaVeiculoNaoEncontradoException e) {
			throw new NegocioException(e.getMessage());
		}
	}
	
	@DeleteMapping("/{modeloVeiculoId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long modeloVeiculoId) {
		cadastroModeloVeiculo.excluir(modeloVeiculoId);
	}
	
	@PutMapping("/status")
	@ResponseStatus(HttpStatus.OK)
	public void atualizarStatus(@RequestBody AtivarDesativarModeloVeiculoInput 
			ativarDesativarModeloVeiculoInput) {
			cadastroModeloVeiculo.alterarStatus(
					ativarDesativarModeloVeiculoInput.getModeloVeiculoIds(), 
					ativarDesativarModeloVeiculoInput.getStatus());
	}
	
	//CAMPOS QUE PODEM SER ORDENADOS PELO ELEMENTO SORT
	private Pageable traduzirPageable(Pageable apiPageable) {
		var mapeamento = Map.of(
				"id", "id",
				"nome", "nome",
				"potencia", "potencia",
				"tanque", "tanque",
				"marcaVeiculo.nome", "marcaVeiculo.nome",
				"categoriaVeiculo.nome", "categoriaVeiculo.nome"
			);
		
		return PageableTranslator.translate(apiPageable, mapeamento);
	}

}
