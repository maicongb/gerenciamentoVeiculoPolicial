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
import br.gov.df.pm.api.assembler.MotivoBaixaViaturaInputDisassembler;
import br.gov.df.pm.api.assembler.MotivoBaixaViaturaModelAssembler;
import br.gov.df.pm.api.model.MotivoBaixaViaturaModel;
import br.gov.df.pm.api.model.input.AtivarDesativarMotivoBaixaViaturaInput;
import br.gov.df.pm.api.model.input.MotivoBaixaViaturaInput;
import br.gov.df.pm.core.data.PageableTranslator;
import br.gov.df.pm.domain.model.MotivoBaixaViatura;
import br.gov.df.pm.domain.repository.MotivoBaixaViaturaRepository;
import br.gov.df.pm.domain.repository.filter.MotivoBaixaViaturaFilter;
import br.gov.df.pm.domain.service.CadastroMotivoBaixaViaturaService;
import br.gov.df.pm.infrastructure.repository.spec.MotivoBaixaViaturaSpecs;

@RestController
@RequestMapping(path = "/motivo-baixa-viatura", produces = MediaType.APPLICATION_JSON_VALUE)
public class MotivoBaixaViaturaController {
	
	@Autowired
	private MotivoBaixaViaturaRepository motivoBaixaViaturaRepository;
	
	@Autowired
	private MotivoBaixaViaturaModelAssembler motivoBaixaViaturaModelAssembler;
	
	@Autowired
	private MotivoBaixaViaturaInputDisassembler motivoBaixaViaturaInputDisassembler;
	
	@Autowired
	private CadastroMotivoBaixaViaturaService cadastroMotivoBaixaViatura;
	

	@PostMapping
	public Page<MotivoBaixaViaturaModel> pesquisar(@RequestBody(required = false) 
								MotivoBaixaViaturaFilter filtro, Pageable pageable) {
		
		pageable = traduzirPageable(pageable);
		
		Page<MotivoBaixaViatura> motivoBaixaViaturaPage = 
				motivoBaixaViaturaRepository.findAll(
						MotivoBaixaViaturaSpecs.usandoFiltro(filtro), pageable);
		
		List<MotivoBaixaViaturaModel> motivoBaixaViaturaModal = motivoBaixaViaturaModelAssembler.
						toCollectionModel(motivoBaixaViaturaPage.getContent());
		
		Page<MotivoBaixaViaturaModel> motivoBaixaViaturaModelPage = new PageImpl<>(motivoBaixaViaturaModal, 
				pageable, motivoBaixaViaturaPage.getTotalElements());
		
		return motivoBaixaViaturaModelPage;
	}
	
	
	@GetMapping("/{motivoBaixaViaturaId}")
	public MotivoBaixaViaturaModel buscar(@PathVariable Long motivoBaixaViaturaId) {
		MotivoBaixaViatura motivoBaixaViatura = cadastroMotivoBaixaViatura
										  .buscarOuFalhar(motivoBaixaViaturaId);
		
		return motivoBaixaViaturaModelAssembler.toModel(motivoBaixaViatura);
	}
	
	@PostMapping("/salvar")
	@ResponseStatus(HttpStatus.CREATED)
	public MotivoBaixaViaturaModel adicionar(@RequestBody 
			@Valid MotivoBaixaViaturaInput motivoBaixaViaturainput) {
		
		MotivoBaixaViatura motivoBaixaViatura = motivoBaixaViaturaInputDisassembler	
						.toDomainObject(motivoBaixaViaturainput);
		
		motivoBaixaViatura = cadastroMotivoBaixaViatura.salvar(motivoBaixaViatura);
		
		MotivoBaixaViaturaModel motivoBaixaViaturaModel = motivoBaixaViaturaModelAssembler
											.toModel(motivoBaixaViatura);
		
		//ADICIONAR A URI http://localhost:8080/motivo-baixa-viatura/1 NO HEADER
		ResourceUriHelper.addUriInResponseHeader(motivoBaixaViaturaModel.getId());
		
		return motivoBaixaViaturaModel;
		
	}
	
	@PutMapping("/{motivoBaixaViaturaId}")
	public MotivoBaixaViaturaModel atualizar(@RequestBody 
					@Valid MotivoBaixaViaturaInput motivoBaixaViaturaInput,
					@PathVariable Long motivoBaixaViaturaId) {
		
		MotivoBaixaViatura motivoBaixaViaturaAtual = cadastroMotivoBaixaViatura
									.buscarOuFalhar(motivoBaixaViaturaId);
		
		motivoBaixaViaturaInputDisassembler
				.copyToDomainObject(motivoBaixaViaturaInput, motivoBaixaViaturaAtual);
		
		motivoBaixaViaturaAtual = cadastroMotivoBaixaViatura.salvar(motivoBaixaViaturaAtual);
		
		return motivoBaixaViaturaModelAssembler.toModel(motivoBaixaViaturaAtual);
		
	}
	
	@DeleteMapping("/{motivoBaixaViaturaId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long motivoBaixaViaturaId) {
		cadastroMotivoBaixaViatura.excluir(motivoBaixaViaturaId);
	}
	
	@PutMapping("/status")
	@ResponseStatus(HttpStatus.OK)
	public void atualizarStatus(@RequestBody AtivarDesativarMotivoBaixaViaturaInput 
			ativarDesativarMotivoBaixaViaturaInput) {
			cadastroMotivoBaixaViatura.alterarStatus(
				ativarDesativarMotivoBaixaViaturaInput.getMotivoBaixaViaturaIds(), 
				ativarDesativarMotivoBaixaViaturaInput.getStatus());
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
