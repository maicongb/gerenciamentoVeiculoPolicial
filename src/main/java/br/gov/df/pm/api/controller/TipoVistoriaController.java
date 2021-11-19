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
import br.gov.df.pm.api.assembler.TipoVistoriaInputDisassembler;
import br.gov.df.pm.api.assembler.TipoVistoriaModelAssembler;
import br.gov.df.pm.api.model.TipoVistoriaModel;
import br.gov.df.pm.api.model.input.AtivarDesativarTipoVistoriaInput;
import br.gov.df.pm.api.model.input.TipoVistoriaInput;
import br.gov.df.pm.core.data.PageableTranslator;
import br.gov.df.pm.domain.model.TipoVistoria;
import br.gov.df.pm.domain.repository.TipoVistoriaRepository;
import br.gov.df.pm.domain.repository.filter.TipoVistoriaFilter;
import br.gov.df.pm.domain.service.CadastroTipoVistoriaService;
import br.gov.df.pm.infrastructure.repository.spec.TipoVistoriaSpecs;

@RestController
@RequestMapping(path = "/tipo-vistoria", produces = MediaType.APPLICATION_JSON_VALUE)
public class TipoVistoriaController {
	
	@Autowired
	private TipoVistoriaRepository tipoVistoriaRepository;
	
	@Autowired
	private TipoVistoriaModelAssembler tipoVistoriaModelAssembler;
	
	@Autowired
	private TipoVistoriaInputDisassembler tipoVistoriaInputDisassembler;
	
	@Autowired
	private CadastroTipoVistoriaService cadastroTipoVistoria;
	

	@PostMapping
	public Page<TipoVistoriaModel> filtrar(@RequestBody(required = false) 
								TipoVistoriaFilter filtro, Pageable pageable) {
		
		pageable = traduzirPageable(pageable);
		
		Page<TipoVistoria> tipoVistoriaPage = 
				tipoVistoriaRepository.findAll(TipoVistoriaSpecs.usandoFiltro(filtro), pageable);
		
		List<TipoVistoriaModel> tipoVistoriaModal = tipoVistoriaModelAssembler.
						toCollectionModel(tipoVistoriaPage.getContent());
		
		Page<TipoVistoriaModel> tipoVistoriaModelPage = new PageImpl<>(tipoVistoriaModal, 
				pageable, tipoVistoriaPage.getTotalElements());
		
		return tipoVistoriaModelPage;
		
	}
	
	@GetMapping("/{tipoVistoriaId}")
	public TipoVistoriaModel buscar(@PathVariable Long tipoVistoriaId) {
		TipoVistoria tipoVistoria = cadastroTipoVistoria
										  .buscarOuFalhar(tipoVistoriaId);
		
		return tipoVistoriaModelAssembler.toModel(tipoVistoria);
	}
	
	@GetMapping("/ativas")
	public List<TipoVistoriaModel> buscarAtivas() {
		List<TipoVistoria> tipoVistoria = tipoVistoriaRepository.findByAtivo(1);
		
		return tipoVistoriaModelAssembler.toCollectionModel(tipoVistoria);
	}
	
	@PostMapping("/salvar")
	@ResponseStatus(HttpStatus.CREATED)
	public TipoVistoriaModel adicionar(@RequestBody 
			@Valid TipoVistoriaInput tipoVistoriaInput) {
		
		TipoVistoria tipoVistoria = tipoVistoriaInputDisassembler	
						.toDomainObject(tipoVistoriaInput);
		
		tipoVistoria = cadastroTipoVistoria.salvar(tipoVistoria);
		
		TipoVistoriaModel tipoVistoriaModel = tipoVistoriaModelAssembler.toModel(tipoVistoria);
		
		//ADICIONAR A URI http://localhost:8080/tipo-viatura/1 NO HEADER
		ResourceUriHelper.addUriInResponseHeader(tipoVistoriaModel.getId());
		
		return tipoVistoriaModel;
		
	}
	
	@PutMapping("/{tipoVistoriaId}")
	public TipoVistoriaModel atualizar(@RequestBody 
					@Valid TipoVistoriaInput tipoVistoriaInput,
					@PathVariable Long tipoVistoriaId) {
		
		TipoVistoria tipoVistoriaAtual = cadastroTipoVistoria
									.buscarOuFalhar(tipoVistoriaId);
		
		tipoVistoriaInputDisassembler
				.copyToDomainObject(tipoVistoriaInput, tipoVistoriaAtual);
		
		tipoVistoriaAtual = cadastroTipoVistoria.salvar(tipoVistoriaAtual);
		
		return tipoVistoriaModelAssembler.toModel(tipoVistoriaAtual);
		
	}
	
	@DeleteMapping("/{tipoVistoriaId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long tipoVistoriaId) {
		cadastroTipoVistoria.excluir(tipoVistoriaId);
	}
	
	@PutMapping("/status")
	@ResponseStatus(HttpStatus.OK)
	public void atualizarStatus(@RequestBody AtivarDesativarTipoVistoriaInput 
			ativarDesativarTipoVistoria) {
		cadastroTipoVistoria.alterarStatus(ativarDesativarTipoVistoria.getTipoVistoriaIds(), 
				ativarDesativarTipoVistoria.getStatus());
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
