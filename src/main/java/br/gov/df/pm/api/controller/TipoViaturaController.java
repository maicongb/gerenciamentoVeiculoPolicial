package br.gov.df.pm.api.controller;

import java.util.List;

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
import br.gov.df.pm.api.assembler.TipoViaturaInputDisassembler;
import br.gov.df.pm.api.assembler.TipoViaturaModelAssembler;
import br.gov.df.pm.api.model.TipoViaturaModel;
import br.gov.df.pm.api.model.input.AtivarDesativarTipoViaturaInput;
import br.gov.df.pm.api.model.input.TipoViaturaInput;
import br.gov.df.pm.domain.model.TipoViatura;
import br.gov.df.pm.domain.repository.TipoViaturaRepository;
import br.gov.df.pm.domain.repository.filter.TipoViaturaFilter;
import br.gov.df.pm.domain.service.CadastroTipoViaturaService;
import br.gov.df.pm.infrastructure.repository.spec.TipoViaturaSpecs;

@RestController
@RequestMapping(path = "/tipo-viatura", produces = MediaType.APPLICATION_JSON_VALUE)
public class TipoViaturaController {
	
	@Autowired
	private TipoViaturaRepository tipoViaturaRepository;
	
	@Autowired
	private TipoViaturaModelAssembler tipoViaturaModelAssembler;
	
	@Autowired
	private TipoViaturaInputDisassembler tipoViaturaInputDisassembler;
	
	@Autowired
	private CadastroTipoViaturaService cadastroTipoViatura;
	

	@PostMapping
	public Page<TipoViaturaModel> listar(@RequestBody(required = false) 
								TipoViaturaFilter filtro, Pageable pageable) {
		
		Page<TipoViatura> tipoViaturaPage = 
				tipoViaturaRepository.findAll(TipoViaturaSpecs.usandoFiltro(filtro), pageable);
		
		List<TipoViaturaModel> tipoViaturaModel = tipoViaturaModelAssembler
				.toCollectionModel(tipoViaturaPage.getContent());
		
		Page<TipoViaturaModel> tipoViaturaModelPage = new PageImpl<>(tipoViaturaModel, 
				pageable, tipoViaturaPage.getTotalElements());
		
		return tipoViaturaModelPage;
	}
	
	@GetMapping("/{tipoViaturaId}")
	public TipoViaturaModel buscar(@PathVariable Long tipoViaturaId) {
		TipoViatura tipoViatura = cadastroTipoViatura
										  .buscarOuFalhar(tipoViaturaId);
		
		return tipoViaturaModelAssembler.toModel(tipoViatura);
	}
	
	@PostMapping("/salvar")
	@ResponseStatus(HttpStatus.CREATED)
	public TipoViaturaModel adicionar(@RequestBody 
			@Valid TipoViaturaInput tipoViaturaInput) {
		
		TipoViatura tipoViatura = tipoViaturaInputDisassembler	
						.toDomainObject(tipoViaturaInput);
		
		tipoViatura = cadastroTipoViatura.salvar(tipoViatura);
		
		TipoViaturaModel tipoViaturaModel = tipoViaturaModelAssembler.toModel(tipoViatura);
		
		//ADICIONAR A URI http://localhost:8080/tipo-viatura/1 NO HEADER
		ResourceUriHelper.addUriInResponseHeader(tipoViaturaModel.getId());
		
		return tipoViaturaModel;
		
	}
	
	@PutMapping("/{tipoViaturaId}")
	public TipoViaturaModel atualizar(@RequestBody 
					@Valid TipoViaturaInput tipoViaturaInput,
					@PathVariable Long tipoViaturaId) {
		
		TipoViatura tipoViaturaAtual = cadastroTipoViatura
									.buscarOuFalhar(tipoViaturaId);
		
		tipoViaturaInputDisassembler
				.copyToDomainObject(tipoViaturaInput, tipoViaturaAtual);
		
		tipoViaturaAtual = cadastroTipoViatura.salvar(tipoViaturaAtual);
		
		return tipoViaturaModelAssembler.toModel(tipoViaturaAtual);
		
	}
	
	@DeleteMapping("/{tipoCombustivelId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long tipoCombustivelId) {
		cadastroTipoViatura.excluir(tipoCombustivelId);
	}
	
	@PutMapping("/status")
	@ResponseStatus(HttpStatus.OK)
	public void atualizarStatus(@RequestBody AtivarDesativarTipoViaturaInput 
			ativarDesativarTipoViatura) {
		cadastroTipoViatura.alterarStatus(ativarDesativarTipoViatura.getTipoViaturaIds(), 
				ativarDesativarTipoViatura.getStatus());
	}
	
}
