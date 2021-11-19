package br.gov.df.pm.api.controller;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
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
import br.gov.df.pm.api.assembler.CategoriaVeiculoInputDisassembler;
import br.gov.df.pm.api.assembler.CategoriaVeiculoModelAssembler;
import br.gov.df.pm.api.model.CategoriaVeiculoModel;
import br.gov.df.pm.api.model.input.AtivarDesativarCategoriaVeiculoInput;
import br.gov.df.pm.api.model.input.CategoriaVeiculoInput;
import br.gov.df.pm.api.openapi.controller.CategoriaVeiculoControllerOpenApi;
import br.gov.df.pm.core.data.PageableTranslator;
import br.gov.df.pm.domain.model.CategoriaVeiculo;
import br.gov.df.pm.domain.repository.CategoriaVeiculoRepository;
import br.gov.df.pm.domain.repository.filter.CategoriaVeiculoFilter;
import br.gov.df.pm.domain.service.CadastroCategoriaVeiculoService;
import br.gov.df.pm.infrastructure.repository.spec.CategoriaVeiculoSpecs;


@RestController
@RequestMapping(path = "/categoria-veiculo", produces = MediaType.APPLICATION_JSON_VALUE)
public class CategoriaVeiculoController implements CategoriaVeiculoControllerOpenApi {
	
	@Autowired
	private CategoriaVeiculoRepository categoriaVeiculoRepository;
	
	@Autowired
	private CategoriaVeiculoModelAssembler categoriaVeiculoModelAssembler;
	
	@Autowired
	private CategoriaVeiculoInputDisassembler categoriaVeiculoInputDisassembler;
	
	@Autowired
	private CadastroCategoriaVeiculoService cadastroCategoriaVeiculo;
	
	@PostMapping
	public Page<CategoriaVeiculoModel> filtrar(@RequestBody(required = false) 
								CategoriaVeiculoFilter filtro, Pageable pageable) {
		
		pageable = traduzirPageable(pageable);
		
		Page<CategoriaVeiculo> categoriaVeiculoPage = 
				categoriaVeiculoRepository.findAll(CategoriaVeiculoSpecs.usandoFiltro(filtro), pageable);
		
		List<CategoriaVeiculoModel> categoriaVeiculoModal = categoriaVeiculoModelAssembler.
						toCollectionModel(categoriaVeiculoPage.getContent());
		
		Page<CategoriaVeiculoModel> categoriaVeiculoModelPage = new PageImpl<>(categoriaVeiculoModal, 
				pageable, categoriaVeiculoPage.getTotalElements());
		
		return categoriaVeiculoModelPage;
	}
	
	@GetMapping("/{categoriaVeiculoId}")
	public CategoriaVeiculoModel buscar(@PathVariable Long categoriaVeiculoId) {
		
		CategoriaVeiculo categoriaVeiculo = cadastroCategoriaVeiculo
										  .buscarOuFalhar(categoriaVeiculoId);
		
		return categoriaVeiculoModelAssembler.toModel(categoriaVeiculo);
	}
	
	@PostMapping("/salvar")
	@ResponseStatus(HttpStatus.CREATED)
	public CategoriaVeiculoModel adicionar(@RequestBody 
			@Valid CategoriaVeiculoInput categoriaVeiculoInput) {
		
		CategoriaVeiculo categoriaVeiculo = categoriaVeiculoInputDisassembler	
						.toDomainObject(categoriaVeiculoInput);
		
		categoriaVeiculo = cadastroCategoriaVeiculo.salvar(categoriaVeiculo);
		
		CategoriaVeiculoModel categoriaVeiculoModel = categoriaVeiculoModelAssembler.toModel(categoriaVeiculo);
				
		//ADICIONAR A URI http://localhost:8080/categoria-veiculo/1 NO HEADER
		ResourceUriHelper.addUriInResponseHeader(categoriaVeiculoModel.getId());
		
		return categoriaVeiculoModel;
		
	}
	

	@PutMapping("/{categoriaVeiculoId}")
	public CategoriaVeiculoModel atualizar(@PathVariable Long categoriaVeiculoId,
			@RequestBody @Valid CategoriaVeiculoInput categoriaVeiculoInput) {
		
		CategoriaVeiculo categoriaVeiculoAtual = cadastroCategoriaVeiculo
									.buscarOuFalhar(categoriaVeiculoId);
		
		categoriaVeiculoInputDisassembler
				.copyToDomainObject(categoriaVeiculoInput, categoriaVeiculoAtual);
		
		categoriaVeiculoAtual = cadastroCategoriaVeiculo.salvar(categoriaVeiculoAtual);
		
		return categoriaVeiculoModelAssembler.toModel(categoriaVeiculoAtual);
		
	}
	

	@DeleteMapping("/{categoriaVeiculoId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long categoriaVeiculoId) {
		
		cadastroCategoriaVeiculo.excluir(categoriaVeiculoId);
	}
	
	@PutMapping("/status")
	@ResponseStatus(HttpStatus.OK)
	public void atualizarStatus(			
			@RequestBody AtivarDesativarCategoriaVeiculoInput 
			ativarDesativarCategoriaVeiculo) {
		
		cadastroCategoriaVeiculo.alterarStatus(ativarDesativarCategoriaVeiculo.getCategoriaVeiculoIds(), 
				ativarDesativarCategoriaVeiculo.getStatus());
	}
	
	
	//CAMPOS QUE PODEM SER ORDENADOS PELO ELEMENTO SORT
	private Pageable traduzirPageable(Pageable apiPageable) {
		var mapeamento = Map.of(
				"id", "id",
				"nome", "nome",
				"ativo", "ativo"
			);
		
		return PageableTranslator.translate(apiPageable, mapeamento);
	}
	
}
