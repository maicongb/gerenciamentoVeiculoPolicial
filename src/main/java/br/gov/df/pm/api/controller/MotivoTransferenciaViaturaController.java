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
import br.gov.df.pm.api.assembler.MotivoTransferenciaViaturaInputDisassembler;
import br.gov.df.pm.api.assembler.MotivoTransferenciaViaturaModelAssembler;
import br.gov.df.pm.api.model.MotivoTransferenciaViaturaModel;
import br.gov.df.pm.api.model.input.AtivarDesativarMotivoTransferenciaViaturalInput;
import br.gov.df.pm.api.model.input.MotivoTransferenciaViaturaInput;
import br.gov.df.pm.core.data.PageableTranslator;
import br.gov.df.pm.domain.model.MotivoTransferenciaViatura;
import br.gov.df.pm.domain.repository.MotivoTransferenciaViaturaRepository;
import br.gov.df.pm.domain.repository.filter.MotivoTransferenciaViaturaFilter;
import br.gov.df.pm.domain.service.CadastroMotivoTransferenciaViaturaService;
import br.gov.df.pm.infrastructure.repository.spec.MotivoTransferenciaViaturaSpecs;

@RestController
@RequestMapping(path = "/motivo-transferencia-viatura", produces = MediaType.APPLICATION_JSON_VALUE)
public class MotivoTransferenciaViaturaController {
	
	@Autowired
	private MotivoTransferenciaViaturaRepository motivoTransferenciaViaturaRepository;
	
	@Autowired
	private MotivoTransferenciaViaturaModelAssembler motivoTransferenciaViaturaModelAssembler;
	
	@Autowired
	private CadastroMotivoTransferenciaViaturaService cadastroMotivoTransferenciaViatura;
	
	@Autowired
	private MotivoTransferenciaViaturaInputDisassembler motivoTransferenciaViaturaInputDisassembler;

	@PostMapping
	private Page<MotivoTransferenciaViaturaModel> filtrar(@RequestBody(required = false)
								MotivoTransferenciaViaturaFilter filtro, Pageable pageable) {
		
		pageable = traduzirPageable(pageable);
		
		Page<MotivoTransferenciaViatura> motivoTransferenciaViaturaPage = 
				motivoTransferenciaViaturaRepository.findAll(
						MotivoTransferenciaViaturaSpecs.usandoFiltro(filtro), pageable);
		
		List<MotivoTransferenciaViaturaModel> motivoTransferenciaViaturaModal = 
				motivoTransferenciaViaturaModelAssembler.toCollectionModel(motivoTransferenciaViaturaPage.getContent());
		
		Page<MotivoTransferenciaViaturaModel> motivoTransferenciaViaturaModelPage = 
				new PageImpl<>(motivoTransferenciaViaturaModal, 
				pageable, motivoTransferenciaViaturaPage.getTotalElements());

		return motivoTransferenciaViaturaModelPage;
	}
	
	
	@GetMapping("/{motivoId}")
	private MotivoTransferenciaViaturaModel buscar(@PathVariable Long motivoId) {
		MotivoTransferenciaViatura motivo = cadastroMotivoTransferenciaViatura.
					buscarOuFalhar(motivoId);
		
		return motivoTransferenciaViaturaModelAssembler.toModel(motivo);
	}
	
	@PostMapping("/salvar")
	@ResponseStatus(HttpStatus.CREATED)
	public MotivoTransferenciaViaturaModel adicionar(@RequestBody 
			@Valid MotivoTransferenciaViaturaInput motivoTransferenciaViaturaInput) {
		
		MotivoTransferenciaViatura motivo = motivoTransferenciaViaturaInputDisassembler	
						.toDomainObject(motivoTransferenciaViaturaInput);
		
		motivo = cadastroMotivoTransferenciaViatura.salvar(motivo);
		
		MotivoTransferenciaViaturaModel motivoTransferenciaViaturaModel = 
				motivoTransferenciaViaturaModelAssembler.toModel(motivo);
		
		//ADICIONAR A URI http://localhost:8080/motivo-transferencia-viatura/1 NO HEADER
		ResourceUriHelper.addUriInResponseHeader(motivoTransferenciaViaturaModel.getId());
		
		return motivoTransferenciaViaturaModel;
		
	}
	
	@PutMapping("/{motivoId}")
	public MotivoTransferenciaViaturaModel atualizar(@RequestBody 
					@Valid MotivoTransferenciaViaturaInput motivoTransferenciaViaturaInput,
					@PathVariable Long motivoId) {
		
		MotivoTransferenciaViatura motivoTransferenciaViaturaAtual = cadastroMotivoTransferenciaViatura
									.buscarOuFalhar(motivoId);
		
		motivoTransferenciaViaturaInputDisassembler
				.copyToDomainObject(motivoTransferenciaViaturaInput, motivoTransferenciaViaturaAtual);
		
		motivoTransferenciaViaturaAtual = cadastroMotivoTransferenciaViatura
											.salvar(motivoTransferenciaViaturaAtual);
		
		return motivoTransferenciaViaturaModelAssembler.toModel(motivoTransferenciaViaturaAtual);
		
	}
	
	@DeleteMapping("/{motivoId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long motivoId) {
		cadastroMotivoTransferenciaViatura.excluir(motivoId);
	}
	
	@PutMapping("/status")
	@ResponseStatus(HttpStatus.OK)
	public void atualizarStatus(@RequestBody AtivarDesativarMotivoTransferenciaViaturalInput 
			ativarDesativarMotivoTransferenciaViaturalInput) {
		
		
		cadastroMotivoTransferenciaViatura.alterarStatus(
				ativarDesativarMotivoTransferenciaViaturalInput.getMotivoTransferenciaViaturaIds(), 
				ativarDesativarMotivoTransferenciaViaturalInput.getStatus());
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
