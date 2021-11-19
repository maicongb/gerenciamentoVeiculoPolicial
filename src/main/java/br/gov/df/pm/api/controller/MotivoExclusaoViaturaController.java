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
import br.gov.df.pm.api.assembler.MotivoExclusaoViaturaInputDisassembler;
import br.gov.df.pm.api.assembler.MotivoExclusaoViaturaModelAssembler;
import br.gov.df.pm.api.model.MotivoExclusaoViaturaModel;
import br.gov.df.pm.api.model.input.AtivarDesativarMotivoExclusaoViaturaInput;
import br.gov.df.pm.api.model.input.MotivoExclusaoViaturaInput;
import br.gov.df.pm.core.data.PageableTranslator;
import br.gov.df.pm.domain.model.MotivoExclusaoViatura;
import br.gov.df.pm.domain.repository.MotivoExclusaoViaturaRepository;
import br.gov.df.pm.domain.repository.filter.MotivoExclusaoViaturaFilter;
import br.gov.df.pm.domain.service.CadastroMotivoExclusaoViaturaService;
import br.gov.df.pm.infrastructure.repository.spec.MotivoExclusaoViaturaSpecs;

@RestController
@RequestMapping(path = "/motivo-exclusao-viatura", produces = MediaType.APPLICATION_JSON_VALUE)
public class MotivoExclusaoViaturaController {
	
	@Autowired
	private MotivoExclusaoViaturaRepository motivoExclusaoViaturaRepository;
	
	@Autowired
	private MotivoExclusaoViaturaModelAssembler motivoExclusaoViaturaModelAssembler;
	
	@Autowired
	private MotivoExclusaoViaturaInputDisassembler motivoExclusaoViaturaInputDisassembler;
	
	@Autowired
	private CadastroMotivoExclusaoViaturaService cadastroMotivoExclusaoViatura;
	

	@PostMapping
	public Page<MotivoExclusaoViaturaModel> filtrar(@RequestBody(required = false)
									MotivoExclusaoViaturaFilter filtro, Pageable pageable) {
		
		pageable = traduzirPageable(pageable);
		
		Page<MotivoExclusaoViatura> motivoExclusaoViaturaPage = 
				motivoExclusaoViaturaRepository.
						findAll(MotivoExclusaoViaturaSpecs.usandoFiltro(filtro), pageable);
		
		List<MotivoExclusaoViaturaModel> motivoExclusaoViaturaModal = 
				motivoExclusaoViaturaModelAssembler.toCollectionModel(motivoExclusaoViaturaPage.getContent());
		
		Page<MotivoExclusaoViaturaModel> motivoExclusaoViaturaModelPage = 
							new PageImpl<>(motivoExclusaoViaturaModal, 
							pageable, motivoExclusaoViaturaPage.getTotalElements());
		
		return motivoExclusaoViaturaModelPage;
	}
	
	@GetMapping("/{motivoExclusaoViaturaId}")
	public MotivoExclusaoViaturaModel buscar(@PathVariable Long motivoExclusaoViaturaId) {
		
		MotivoExclusaoViatura motivoExclusaoViatura = cadastroMotivoExclusaoViatura
										  .buscarOuFalhar(motivoExclusaoViaturaId);
		
		return motivoExclusaoViaturaModelAssembler.toModel(motivoExclusaoViatura);
	}
	
	@PostMapping("/salvar")
	@ResponseStatus(HttpStatus.CREATED)
	public MotivoExclusaoViaturaModel adicionar(@RequestBody 
			@Valid MotivoExclusaoViaturaInput motivoExclusaoViaturainput) {
		
		MotivoExclusaoViatura motivoExclusaoViatura = motivoExclusaoViaturaInputDisassembler	
						.toDomainObject(motivoExclusaoViaturainput);
		
		motivoExclusaoViatura = cadastroMotivoExclusaoViatura.salvar(motivoExclusaoViatura);
		
		MotivoExclusaoViaturaModel motivoExclusaoViaturaModel = motivoExclusaoViaturaModelAssembler
												.toModel(motivoExclusaoViatura);
		
		//ADICIONAR A URI http://localhost:8080/motivo-exclusao-viatura/1 NO HEADER
		ResourceUriHelper.addUriInResponseHeader(motivoExclusaoViaturaModel.getId());
		
		return motivoExclusaoViaturaModel;
		
	}
	
	@PutMapping("/{motivoExclusaoViaturaId}")
	public MotivoExclusaoViaturaModel atualizar(@RequestBody 
					@Valid MotivoExclusaoViaturaInput motivoExclusaoViaturaInput,
					@PathVariable Long motivoExclusaoViaturaId) {
		
		MotivoExclusaoViatura motivoExclusaoViaturaAtual = cadastroMotivoExclusaoViatura
									.buscarOuFalhar(motivoExclusaoViaturaId);
		
		motivoExclusaoViaturaInputDisassembler
				.copyToDomainObject(motivoExclusaoViaturaInput, motivoExclusaoViaturaAtual);
		
		motivoExclusaoViaturaAtual = cadastroMotivoExclusaoViatura.salvar(motivoExclusaoViaturaAtual);
		
		return motivoExclusaoViaturaModelAssembler.toModel(motivoExclusaoViaturaAtual);
		
	}
	
	@DeleteMapping("/{motivoExclusaoViaturaId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long motivoExclusaoViaturaId) {
		cadastroMotivoExclusaoViatura.excluir(motivoExclusaoViaturaId);
	}
	
	@PutMapping("/status")
	@ResponseStatus(HttpStatus.OK)
	public void atualizarStatus(@RequestBody AtivarDesativarMotivoExclusaoViaturaInput 
			ativarDesativarMotivoExclusaoViaturaInput) {
			cadastroMotivoExclusaoViatura.alterarStatus(
				ativarDesativarMotivoExclusaoViaturaInput.getMotivoExclusaoViaturaIds(), 
				ativarDesativarMotivoExclusaoViaturaInput.getStatus());
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
