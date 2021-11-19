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
import br.gov.df.pm.api.assembler.TipoVeiculoInputDisassembler;
import br.gov.df.pm.api.assembler.TipoVeiculoModelAssembler;
import br.gov.df.pm.api.model.TipoVeiculoModel;
import br.gov.df.pm.api.model.input.AtivarDesativarTipoVeiculoInput;
import br.gov.df.pm.api.model.input.TipoVeiculoInput;
import br.gov.df.pm.core.data.PageableTranslator;
import br.gov.df.pm.domain.model.TipoVeiculo;
import br.gov.df.pm.domain.repository.TipoVeiculoRepository;
import br.gov.df.pm.domain.repository.filter.TipoVeiculoFilter;
import br.gov.df.pm.domain.service.CadastroTipoVeiculoService;
import br.gov.df.pm.infrastructure.repository.spec.TipoVeiculoSpecs;

@RestController
@RequestMapping(path = "/tipo-veiculo", produces = MediaType.APPLICATION_JSON_VALUE)
public class TipoVeiculoController {
	
	@Autowired
	private TipoVeiculoRepository tipoVeiculoRepository;
	
	@Autowired
	private TipoVeiculoModelAssembler tipoVeiculoModelAssembler;
	
	@Autowired
	private TipoVeiculoInputDisassembler tipoVeiculoInputDisassembler;
	
	@Autowired
	private CadastroTipoVeiculoService cadastroTipoVeiculo;
	

	@PostMapping
	public Page<TipoVeiculoModel> filtrar(@RequestBody(required = false) 
									TipoVeiculoFilter filtro, Pageable pageable) {

		pageable = traduzirPageable(pageable);
		
		Page<TipoVeiculo> tipoVeiculoPage = 
				tipoVeiculoRepository.findAll(TipoVeiculoSpecs.usandoFiltro(filtro), pageable);
		
		List<TipoVeiculoModel> tipoVeiculoModal = tipoVeiculoModelAssembler.
						toCollectionModel(tipoVeiculoPage.getContent());
		
		Page<TipoVeiculoModel> tipoVeiculoModelPage = new PageImpl<>(tipoVeiculoModal, 
				pageable, tipoVeiculoPage.getTotalElements());
		
		return tipoVeiculoModelPage;
	}
	
	@GetMapping("/ativos")
	public List<TipoVeiculoModel> listarAtivos() {
		List<TipoVeiculo> tipoVeiculo = tipoVeiculoRepository.findByAtivoOrderByNome(1);
		
		return tipoVeiculoModelAssembler.toCollectionModel(tipoVeiculo);
	}
	
	@GetMapping("/{tipoVeiculoId}")
	public TipoVeiculoModel buscar(@PathVariable Long tipoVeiculoId) {
		TipoVeiculo tipoVeiculo = cadastroTipoVeiculo
										  .buscarOuFalhar(tipoVeiculoId);
		
		return tipoVeiculoModelAssembler.toModel(tipoVeiculo);
	}
	
	@PostMapping("/salvar")
	@ResponseStatus(HttpStatus.CREATED)
	public TipoVeiculoModel adicionar(@RequestBody 
			@Valid TipoVeiculoInput tipoVeiculoInput) {
		
		TipoVeiculo tipoVeiculo = tipoVeiculoInputDisassembler	
						.toDomainObject(tipoVeiculoInput);
		
		tipoVeiculo = cadastroTipoVeiculo.salvar(tipoVeiculo);
		
		TipoVeiculoModel tipoVeiculoModel = tipoVeiculoModelAssembler.toModel(tipoVeiculo);
		
		//ADICIONAR A URI http://localhost:8080/tipo-veiculo/1 NO HEADER
		ResourceUriHelper.addUriInResponseHeader(tipoVeiculoModel.getId());
		
		return tipoVeiculoModel;
		
	}
	
	@PutMapping("/{tipoVeiculoId}")
	public TipoVeiculoModel atualizar(@PathVariable Long tipoVeiculoId,
				@RequestBody @Valid TipoVeiculoInput tipoVeiculoInput) {
		
		TipoVeiculo tipoVeiculoAtual = cadastroTipoVeiculo
									.buscarOuFalhar(tipoVeiculoInput.getId());
		
		tipoVeiculoInputDisassembler
				.copyToDomainObject(tipoVeiculoInput, tipoVeiculoAtual);
		
		tipoVeiculoAtual = cadastroTipoVeiculo.salvar(tipoVeiculoAtual);
		
		return tipoVeiculoModelAssembler.toModel(tipoVeiculoAtual);
		
	}

	
	@DeleteMapping("/{tipoVeiculoId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long tipoVeiculoId) {
		cadastroTipoVeiculo.excluir(tipoVeiculoId);
	}
	
	@PutMapping("/status")
	@ResponseStatus(HttpStatus.OK)
	public void atualizarStatus(@RequestBody AtivarDesativarTipoVeiculoInput 
			ativarDesativarTipoVeiculo) {
		cadastroTipoVeiculo.alterarStatus(ativarDesativarTipoVeiculo.getTipoVeiculoIds(), 
				ativarDesativarTipoVeiculo.getStatus());
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
