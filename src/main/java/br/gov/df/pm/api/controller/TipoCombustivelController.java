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
import br.gov.df.pm.api.assembler.TipoCombustivelInputDisassembler;
import br.gov.df.pm.api.assembler.TipoCombustivelModelAssembler;
import br.gov.df.pm.api.model.TipoCombustivelModel;
import br.gov.df.pm.api.model.input.AtivarDesativarTipoCombustivelInput;
import br.gov.df.pm.api.model.input.TipoCombustivelInput;
import br.gov.df.pm.core.data.PageableTranslator;
import br.gov.df.pm.domain.model.TipoCombustivel;
import br.gov.df.pm.domain.repository.TipoCombustivelRepository;
import br.gov.df.pm.domain.repository.filter.TipoCombustivelFilter;
import br.gov.df.pm.domain.service.CadastroTipoCombustivelService;
import br.gov.df.pm.infrastructure.repository.spec.TipoCombustivelSpecs;

@RestController
@RequestMapping(path = "/tipo-combustivel", produces = MediaType.APPLICATION_JSON_VALUE)
public class TipoCombustivelController {
	
	@Autowired
	private TipoCombustivelRepository tipoCombustivelRepository;
	
	@Autowired
	private TipoCombustivelModelAssembler tipoCombustivelModelAssembler;
	
	@Autowired
	private TipoCombustivelInputDisassembler tipoCombustivelInputDisassembler;
	
	@Autowired
	private CadastroTipoCombustivelService cadastroTipoComputivel;
	

	@PostMapping
	public Page<TipoCombustivelModel> filtrar(@RequestBody(required = false) 
							TipoCombustivelFilter filtro, Pageable pageable) {
		
		pageable = traduzirPageable(pageable);
		
		Page<TipoCombustivel> tipoCombustivelPage = 
				tipoCombustivelRepository.findAll(TipoCombustivelSpecs.usandoFiltro(filtro), pageable);
		
		List<TipoCombustivelModel> tipoCombustivelModal = tipoCombustivelModelAssembler.
						toCollectionModel(tipoCombustivelPage.getContent());
		
		Page<TipoCombustivelModel> tipoCombustivelModelPage = new PageImpl<>(tipoCombustivelModal, 
				pageable, tipoCombustivelPage.getTotalElements());
		
		return tipoCombustivelModelPage;
	}
	
	@GetMapping("/{tipoCombustivelId}")
	public TipoCombustivelModel buscar(@PathVariable Long tipoCombustivelId) {
		TipoCombustivel tipoCombustivel = cadastroTipoComputivel
										  .buscarOuFalhar(tipoCombustivelId);
		
		return tipoCombustivelModelAssembler.toModel(tipoCombustivel);
	}
	
	@PostMapping("/salvar")
	@ResponseStatus(HttpStatus.CREATED)
	public TipoCombustivelModel adicionar(@RequestBody 
			@Valid TipoCombustivelInput tipoCombustivelinput) {
		
		TipoCombustivel tipoCombustivel = tipoCombustivelInputDisassembler	
						.toDomainObject(tipoCombustivelinput);
		
		tipoCombustivel = cadastroTipoComputivel.salvar(tipoCombustivel);
		
		TipoCombustivelModel tipoCombustivelModel = tipoCombustivelModelAssembler
														.toModel(tipoCombustivel);
		
		//ADICIONAR A URI http://localhost:8080/tipo-combustiveis/1 NO HEADER
		ResourceUriHelper.addUriInResponseHeader(tipoCombustivelModel.getId());
		
		return tipoCombustivelModel;
		
	}
	
	@PutMapping("/{tipoCombustivelId}")
	public TipoCombustivelModel atualizar(@RequestBody 
					@Valid TipoCombustivelInput tipoCombustivelInput,
					@PathVariable Long tipoCombustivelId) {
		
		TipoCombustivel tipoCombustivelAtual = cadastroTipoComputivel
									.buscarOuFalhar(tipoCombustivelId);
		
		tipoCombustivelInputDisassembler
				.copyToDomainObject(tipoCombustivelInput, tipoCombustivelAtual);
		
		tipoCombustivelAtual = cadastroTipoComputivel.salvar(tipoCombustivelAtual);
		
		return tipoCombustivelModelAssembler.toModel(tipoCombustivelAtual);
		
	}
	
	@DeleteMapping("/{tipoCombustivelId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long tipoCombustivelId) {
		cadastroTipoComputivel.excluir(tipoCombustivelId);
	}
	
	@PutMapping("/status")
	@ResponseStatus(HttpStatus.OK)
	public void atualizarStatus(@RequestBody AtivarDesativarTipoCombustivelInput 
			ativarDesativarTipoCombustivel) {
		cadastroTipoComputivel.alterarStatus(ativarDesativarTipoCombustivel.getTipoCombustivelIds(), 
				ativarDesativarTipoCombustivel.getStatus());
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
