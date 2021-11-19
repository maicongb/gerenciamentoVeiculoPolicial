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
import br.gov.df.pm.api.assembler.ItensVistoriaInputDisassembler;
import br.gov.df.pm.api.assembler.ItensVistoriaModelAssembler;
import br.gov.df.pm.api.model.ItensVistoriaModel;
import br.gov.df.pm.api.model.input.AtivarDesativarItensVistoriaInput;
import br.gov.df.pm.api.model.input.ItensVistoriaInput;
import br.gov.df.pm.api.openapi.controller.ItensVistoriaControllerOpenApi;
import br.gov.df.pm.core.data.PageableTranslator;
import br.gov.df.pm.domain.exception.ItensVistoriaNaoEncontradoException;
import br.gov.df.pm.domain.exception.NegocioException;
import br.gov.df.pm.domain.model.ItensVistoria;
import br.gov.df.pm.domain.repository.ItensVistoriaRepository;
import br.gov.df.pm.domain.repository.filter.ItensVistoriaFilter;
import br.gov.df.pm.domain.service.CadastroItensVistoriaService;
import br.gov.df.pm.infrastructure.repository.spec.ItensVistoriaSpecs;

@RestController
@RequestMapping(path = "/item-vistoria", produces = MediaType.APPLICATION_JSON_VALUE)
public class ItensVistoriaController implements ItensVistoriaControllerOpenApi {
	
	@Autowired
	private CadastroItensVistoriaService cadastroItensVistoria;
	
	@Autowired
	private ItensVistoriaModelAssembler itensVistoriaModelAssembler;
	
	@Autowired
	private ItensVistoriaInputDisassembler itensVistoriaInputDisassembler;
	
	@Autowired
	private ItensVistoriaRepository itensVistoriaRepository;
	
	@Override
	@PostMapping
	public Page<ItensVistoriaModel> filtrar(@RequestBody(required = false)
							ItensVistoriaFilter filtro, Pageable pageable) {
		
		
		
		pageable = traduzirPageable(pageable);
		
		Page<ItensVistoria> itemVistoriaPage = 
				itensVistoriaRepository.findAll(ItensVistoriaSpecs.usandoFiltro(filtro), pageable);
		
		List<ItensVistoriaModel> itensVistoriaModal = itensVistoriaModelAssembler.
						toCollectionModel(itemVistoriaPage.getContent());
		
		Page<ItensVistoriaModel> itensVistgoriaModelPage = new PageImpl<>(itensVistoriaModal, 
				pageable, itemVistoriaPage.getTotalElements());
		
		return itensVistgoriaModelPage;
	}
	
	@Override
	@GetMapping("/{itensVistoriaId}")
	public ItensVistoriaModel buscar(@PathVariable Long itensVistoriaId) {
		ItensVistoria itensVistoria = cadastroItensVistoria.buscarOuFalhar(itensVistoriaId);
		
		return itensVistoriaModelAssembler.toModel(itensVistoria);
	}
	
	@Override
	@PostMapping("/salvar")
	@ResponseStatus(HttpStatus.CREATED)
	public ItensVistoriaModel adicionar(@RequestBody @Valid ItensVistoriaInput itensVistoriaInput) {
		
		ItensVistoria itensVistoria = itensVistoriaInputDisassembler
				.toDomainObject(itensVistoriaInput);
		
		itensVistoria = cadastroItensVistoria.salvar(itensVistoria);
		
		ItensVistoriaModel vistoriaModel = itensVistoriaModelAssembler.toModel(itensVistoria);
		
		//ADICIONAR A URI http://localhost:8080/itens-vistoria/1 NO HEADER
		ResourceUriHelper.addUriInResponseHeader(vistoriaModel.getId());
		
		return vistoriaModel;
		
	}
	
	@Override
	@PutMapping("/{itensVistoriaId}")
	public ItensVistoriaModel atualizar(@RequestBody @Valid ItensVistoriaInput itensVistoriaInput,
				@PathVariable Long itensVistoriaId) {
		
		try {
			
			ItensVistoria itensVistoriaAtual = cadastroItensVistoria
					.buscarOuFalhar(itensVistoriaId);
			
			itensVistoriaInputDisassembler.copyToDomainObject(itensVistoriaInput, itensVistoriaAtual);
			
			itensVistoriaAtual = cadastroItensVistoria.salvar(itensVistoriaAtual);
			
			return itensVistoriaModelAssembler.toModel(itensVistoriaAtual);
			
		} catch (ItensVistoriaNaoEncontradoException e) {
			throw new NegocioException(e.getMessage());
		}
	}
	
	@Override
	@DeleteMapping("/{itensVistoriaId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long itensVistoriaId) {
		cadastroItensVistoria.excluir(itensVistoriaId);
	}
	
	@Override
	@PutMapping("/status")
	@ResponseStatus(HttpStatus.OK)
	public void atualizarStatus(@RequestBody AtivarDesativarItensVistoriaInput 
			ativarDesativarItensVistoriaInput) {
			cadastroItensVistoria.alterarStatus(
					ativarDesativarItensVistoriaInput.getItensVistoriaIds(), 
					ativarDesativarItensVistoriaInput.getStatus());
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
